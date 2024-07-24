package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.ProductDTO;
import com.boti.mkdigital.productsidentifier.domain.Category;
import com.boti.mkdigital.productsidentifier.domain.Product;
import com.boti.mkdigital.productsidentifier.domain.ProductInfo;
import com.boti.mkdigital.productsidentifier.domain.Subcategory;
import com.boti.mkdigital.productsidentifier.mapper.ProductMapper;
import com.boti.mkdigital.productsidentifier.repository.CategoryRepository;
import com.boti.mkdigital.productsidentifier.repository.ProductInfoRepository;
import com.boti.mkdigital.productsidentifier.repository.ProductRepository;
import com.boti.mkdigital.productsidentifier.repository.SubcategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.boti.mkdigital.productsidentifier.repository.specs.ProductSpecs.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final ProductInfoRepository productInfoRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductMapper mapper;

    @Transactional
    public void beforeUpdateProducts(String marketplace) {
        repository.markAsInvalidByMarketplace(marketplace);
    }

    @Transactional
    public void afterUpdateProducts(String marketplace) {
        repository.deleteAllByMarketplaceAndValidIsFalse(marketplace);
    }

    @Transactional
    public Product save(Product product) {
        Optional<Product> oldProduct = repository.findByKey(product.getKey());

        if (oldProduct.isPresent()) {
            return repository.save(oldProduct.get().applyChanges(product));
        }
        checkHaveCategory(product);
        product.setValid(true);
        return updateAdsInfo(product);
    }

    @Transactional
    public Product updateAdsInfo(Product product) {
        try {
            ProductInfo productInfo = new ProductInfo();

            String content = Jsoup.connect(product.getUrl()).get().text();
            String analysis = GPT4Analyzer.analyzeContent(content);

            boolean canAdvertiseOnGoogle = !analysis.contains("Google Ads violation");
            boolean canAdvertiseOnBing = !analysis.contains("Bing Ads violation");
            boolean canAdvertiseOnFacebook = !analysis.contains("Facebook Ads violation");

            productInfo.setGoogleAdsAvailable(canAdvertiseOnGoogle);
            productInfo.setBingAdsAvailable(canAdvertiseOnBing);
            productInfo.setFacebookAdsAvailable(canAdvertiseOnFacebook);

            List<String> googleKeywords = extractKeywords(analysis, "Google Ads keywords:");
            List<String> bingKeywords = extractKeywords(analysis, "Bing Ads keywords:");
            List<String> facebookKeywords = extractKeywords(analysis, "Facebook Ads keywords:");

            productInfo.setGoogleKeywords(googleKeywords);
            productInfo.setBingKeywords(bingKeywords);
            productInfo.setFacebookKeywords(facebookKeywords);

            productInfo = productInfoRepository.save(productInfo);
            product.setProductInfo(productInfo);
            return repository.save(product);
        } catch (IOException e) {
            log.error("Error updating ads info for product: {}", product.getKey(), e);
            throw new RuntimeException("Failed to update ads info", e);
        }
    }

    private List<String> extractKeywords(String analysis, String keywordSection) {
        List<String> keywords = new ArrayList<>();
        int startIndex = analysis.indexOf(keywordSection);
        if (startIndex != -1) {
            startIndex += keywordSection.length();
            int endIndex = analysis.indexOf("\n", startIndex);
            if (endIndex == -1) endIndex = analysis.length();
            String keywordStr = analysis.substring(startIndex, endIndex).trim();
            String[] keywordArray = keywordStr.split(",");
            for (String keyword : keywordArray) {
                keywords.add(keyword.trim());
            }
        }
        return keywords;
    }

    @Transactional
    public void checkHaveCategory(Product product) {
        Optional<Category> byCategory = categoryRepository.findByCategoryAndMarketplace(product.getCategory().getCategory(), product.getMarketplace());
        if (byCategory.isPresent()) {
            product.setCategory(byCategory.get());
        } else {
            product.setCategory(categoryRepository.save(Category.builder().marketplace(product.getMarketplace()).category(product.getCategory().getCategory()).build()));
        }
        Optional<Subcategory> tmpSubCategory = subcategoryRepository.findByCategoryIdAndSubCategory(product.getCategory().getId(), product.getSubCategory().getSubCategory());
        if (tmpSubCategory.isPresent()) {
            product.setSubCategory(tmpSubCategory.get());
        } else {
            product.setSubCategory(subcategoryRepository.save(Subcategory.builder().subCategory(product.getSubCategory().getSubCategory()).category(product.getCategory()).build()));
        }
    }

    public Page<ProductDTO> getAllProductsAvailableToAdsPageable(String name, List<Integer> categoryIds, List<Integer> subCategoryIds, String ranking, Double avgInitial, Double avgFinal, boolean upsell, boolean rebill, boolean canAdsGoogle, Pageable page) {
        Specification<Product> spec = Specification.where(hasUpsell(upsell)).and(hasRebill(rebill));
        spec = Objects.nonNull(name) && !Strings.isBlank(name)? spec.and(nameLike(name)) : spec;
        spec = Objects.nonNull(ranking) && !Strings.isBlank(ranking) ? spec.and(rankingEq(ranking)) : spec;
        spec = Objects.nonNull(categoryIds) && categoryIds.size() > 0? spec.and(categoryIdsIn(categoryIds)) : spec;
        spec = Objects.nonNull(subCategoryIds) &&  subCategoryIds.size() > 0? spec.and(subCategoryIdsIn(subCategoryIds)) : spec;
        spec = Objects.nonNull(avgInitial) ? spec.and(avgGreaterThan(avgInitial)) : spec;
        spec = Objects.nonNull(avgFinal) ? spec.and(avgLessThan(avgFinal)) : spec;

        return repository.findAll(spec, page).map(mapper::toDto);
    }
}