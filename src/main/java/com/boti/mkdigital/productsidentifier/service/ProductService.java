package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.ClickBankParamsRequest;
import com.boti.mkdigital.productsidentifier.DTO.ClickBankProductDTO;
import com.boti.mkdigital.productsidentifier.DTO.ClickBankResponse;
import com.boti.mkdigital.productsidentifier.domain.Product;
import com.boti.mkdigital.productsidentifier.feign.client.ClickBankClient;
import com.boti.mkdigital.productsidentifier.mapper.ClickBankProductMapper;
import com.boti.mkdigital.productsidentifier.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final ClickBankClient clickBankClient;

    private final ClickBankProductMapper mapper;

    public void getAllProducts() throws InterruptedException {
        repository.deleteAll();
        ClickBankResponse res = clickBankClient.getProducts(getTotalHitsParams());
        log.info(res.getData().getMarketplaceSearch().getTotalHits() + " produtos na clickBank");
        Integer numberOfCalls = (res.getData().getMarketplaceSearch().getTotalHits() / 50) +1;
        Integer offSet = 0;
        for (Integer call = 1; call <= numberOfCalls; call++) {
            log.info("Baixando produtos do " + offSet + " ao " + call * 50);
            ClickBankResponse callRes = clickBankClient.getProducts(getHitsParams(offSet));
            offSet += 50;
            repository.saveAll(callRes.getData().getMarketplaceSearch().getHits().stream().map(Product::new).collect(Collectors.toList()));
        }
    }

    public void updateIfCanGoogleAds() {
        List<Product> products = repository.findAllByAffiliateToolsUrlIsNotNull();
        AtomicInteger count = new AtomicInteger(1);
        products.stream().filter(p -> Objects.nonNull(p.getAffiliateToolsUrl()) && !p.getAffiliateToolsUrl().isBlank()).collect(Collectors.toList()).forEach(p -> {
                    try {
                        log.info("Validando se contem Google Ads do produto: " + p.getSite() + " numero " + count.getAndIncrement() + " de " + products.size() + " que contem site de affiliados");
                        p.setCanAdsOnGoogle(!readHtmlToFind(p.getAffiliateToolsUrl(), "GOOGLE AD"));
                    } catch (IOException e) {
                    }
                    repository.save(p);
                }
        );
    }

    public boolean readHtmlToFind(String url, String content) throws IOException {
        String html = Jsoup.connect(url).get().html();
        return html.toUpperCase().contains(content);
    }

    private ClickBankParamsRequest getTotalHitsParams() {
        ClickBankParamsRequest params = new ClickBankParamsRequest();
        params.setQuery("query ($parameters: MarketplaceSearchParameters!) {marketplaceSearch(parameters: $parameters) {totalHits}}");
        params.setVariables(params.createEmptyParams());
        return params;
    }

    private ClickBankParamsRequest getHitsParams(Integer offSet) {
        ClickBankParamsRequest params = new ClickBankParamsRequest();
        ClickBankParamsRequest.VariablesClickBank variablesClickBank = params.createEmptyParams();
        variablesClickBank.setParameters(variablesClickBank.createParams(50, offSet, "gravity", false));
        params.setQuery("query ($parameters: MarketplaceSearchParameters!) {\n\t\t\tmarketplaceSearch(parameters: $parameters) {\n\t\t\t\ttotalHits\n\t\t\t\toffset\n\t\t\t\thits {\n\t\t\t\t\tsite\n\t\t\t\t\ttitle\n\t\t\t\t\tdescription\n\t\t\t\t\tfavorite\n\t\t\t\t\turl\n\t\t\t\t\tmarketplaceStats {\n\t\t\t\t\t\tactivateDate\n\t\t\t\t\t\tcategory\n\t\t\t\t\t\tsubCategory\n\t\t\t\t\t\tinitialDollarsPerSale\n\t\t\t\t\t\taverageDollarsPerSale\n\t\t\t\t\t\tgravity\n\t\t\t\t\t\ttotalRebill\n\t\t\t\t\t\tde\n\t\t\t\t\t\ten\n\t\t\t\t\t\tes\n\t\t\t\t\t\tfr\n\t\t\t\t\t\tit\n\t\t\t\t\t\tpt\n\t\t\t\t\t\tstandard\n\t\t\t\t\t\tphysical\n\t\t\t\t\t\trebill\n\t\t\t\t\t\tupsell\n\t\t\t\t\t\tstandardUrlPresent\n\t\t\t\t\t\tmobileEnabled\n\t\t\t\t\t\twhitelistVendor\n\t\t\t\t\t\tcpaVisible\n\t\t\t\t\t\tdollarTrial\n\t\t\t\t\t\thasAdditionalSiteHoplinks\n\t\t\t\t\t}\n\t\t\t \t\taffiliateToolsUrl\n\t\t\t  \t\taffiliateSupportEmail\n            \t\tskypeName\n\t\t\t\t}\n        facets {\n\t\t\t\t\tfield\n\t\t\t\t\tbuckets {\n\t\t\t\t\t\tvalue\n\t\t\t\t\t\tcount\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n    }");
        params.setVariables(variablesClickBank);
        return params;
    }

    public Page<ClickBankProductDTO> getAllProductsAvailableToAdsPageable(Pageable page) {
        return repository.findAllByCanAdsOnGoogleOrderByGravityAscAverageDollarsPerSaleDesc(true,page).map(p -> mapper.toDto(p));
    }
    public List<ClickBankProductDTO> getAllProductsAvailableToAds() {
        return mapper.toDto(repository.findAllByCanAdsOnGoogleOrderByGravityAscAverageDollarsPerSaleDesc(true));
    }

    public byte[] getFile(List<ClickBankProductDTO> clickBankProductDTOS) throws Exception {
        String nameFile = "Licenses generated " + LocalDate.now();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle cs = workbook.createCellStyle();
            List<String> headers = Arrays.asList("Codigo ClickBank","Titulo do anuncio","Descricao","url","Categoria","Sub-categoria","Inicial $/conversao","Media $/conversao","Gravity","Recorrencia","Standard","Fisico","Recorrente","Upsell","Url Afiliados");
            XSSFSheet sheet = workbook.createSheet("licenses"); // creating a blank sheet
            int rownum = 1;
            int HEADER = 0;
            createHeaders(sheet.createRow(HEADER),headers);
            for (ClickBankProductDTO clickBankProductDTO : clickBankProductDTOS) {
                Row row = sheet.createRow(rownum++);
                createList(clickBankProductDTO, row, cs);
            }
            FileOutputStream out = new FileOutputStream(nameFile); // file name with path
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] export = Files.readAllBytes(new File(nameFile).toPath());
        new File(nameFile).delete();
        return export;
    }

    private static void createList(ClickBankProductDTO clickBankProductDTO, Row row, XSSFCellStyle cs) { // creating cells for each row
        AtomicInteger column = new AtomicInteger();
        Cell cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getSite());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getTitle());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getDescription());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getUrl());

//        cell = row.createCell(column.getAndIncrement());
//        cell.setCellValue(Date.from(clickBankProductDTO.getActivateDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getCategory());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getSubCategory());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getInitialDollarsPerSale());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getAverageDollarsPerSale());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getGravity());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getTotalRebill());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.isStandard());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.isPhysical());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.isRebill());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.isUpsell());

        cell = row.createCell(column.getAndIncrement());
        cell.setCellValue(clickBankProductDTO.getAffiliateToolsUrl());
    }

    private static void createHeaders(Row row, List<String> headers) {
        AtomicInteger column = new AtomicInteger();
        AtomicReference<Cell> cell = new AtomicReference<>();
        headers.forEach(h -> {
            cell.set(row.createCell(column.getAndIncrement()));
            cell.get().setCellValue(h);
        });

    }

    public byte[] exportLicenses() throws Exception {
        return getFile(getAllProductsAvailableToAds());
    }
}