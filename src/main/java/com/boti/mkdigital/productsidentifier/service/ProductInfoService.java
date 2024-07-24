package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.domain.Product;
import com.boti.mkdigital.productsidentifier.domain.ProductInfo;
import com.boti.mkdigital.productsidentifier.repository.ProductInfoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductInfoService {
    private final ProductInfoRepository productInfoRepository;

    @Transactional
    public void revalidateProductInfo(Long productId) {
        Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(productId);
        if (productInfoOptional.isPresent()) {
            ProductInfo productInfo = productInfoOptional.get();
            try {
                String content = Jsoup.connect(productInfo.getAffiliateLink()).get().text();
                String analysis = GPT4Analyzer.analyzeContent(content);
                AdAnalysisResponse response = parseAnalysis(analysis);

                productInfo.setGoogleAdsAvailable(response.googleAds().potentialViolations().misleadingClaims() == null);
                productInfo.setGoogleKeywords(response.googleAds().keywords());
                productInfo.setGoogleProducerRestrictions(response.googleAds().producerRestrictions());

                productInfo.setBingAdsAvailable(response.bingAds().potentialViolations().misleadingHealthClaims() == null);
                productInfo.setBingKeywords(response.bingAds().keywords());
                productInfo.setBingProducerRestrictions(response.bingAds().producerRestrictions());

                productInfo.setFacebookAdsAvailable(response.facebookAds().potentialViolations().exaggeratedClaims() == null);
                productInfo.setFacebookKeywords(response.facebookAds().keywords());
                productInfo.setFacebookProducerRestrictions(response.facebookAds().producerRestrictions());

                productInfo = productInfoRepository.save(productInfo);
                productInfoRepository.save(productInfo);
            } catch (IOException e) {
                log.error("Error revalidating product info for product ID: {}", productId, e);
                throw new RuntimeException("Failed to revalidate product info", e);
            }
        } else {
            log.warn("ProductInfo not found for product ID: {}", productId);
            throw new RuntimeException("ProductInfo not found");
        }
    }

    @Transactional(readOnly = true)
    public ProductInfo getProductInfoByUrl(String url) {
        try {
            String content = Jsoup.connect(url).get().html();
            String analysis = GPT4Analyzer.analyzeContent(content);
            ProductInfo productInfo = new ProductInfo();
            AdAnalysisResponse response = parseAnalysis(analysis);

            productInfo.setGoogleAdsAvailable(response.googleAds().potentialViolations().misleadingClaims() == null);
            productInfo.setGoogleKeywords(response.googleAds().keywords());
            productInfo.setGoogleProducerRestrictions(Optional.ofNullable(response.googleAds().producerRestrictions()).orElse(response.googleAds().potentialViolations().producerRestrictions()));

            productInfo.setBingAdsAvailable(response.bingAds().potentialViolations().misleadingHealthClaims() == null);
            productInfo.setBingKeywords(response.bingAds().keywords());
            productInfo.setBingProducerRestrictions(Optional.ofNullable(response.bingAds().producerRestrictions()).orElse(response.bingAds().potentialViolations().producerRestrictions()));

            productInfo.setFacebookAdsAvailable(response.facebookAds().potentialViolations().exaggeratedClaims() == null);
            productInfo.setFacebookKeywords(response.facebookAds().keywords());
            productInfo.setFacebookProducerRestrictions(Optional.ofNullable(response.facebookAds().producerRestrictions()).orElse(response.facebookAds().potentialViolations().producerRestrictions()));

            return productInfo;
        } catch (IOException e) {
            log.error("Error fetching product info for URL: {}", url, e);
            throw new RuntimeException("Failed to fetch product info", e);
        }
    }

    @Transactional(readOnly = true)
    public ProductInfo getProductInfoById(Long productId) {
        return productInfoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("ProductInfo not found for ID: " + productId));
    }

    private AdAnalysisResponse parseAnalysis(String analysis) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Limpeza da string de análise
        String cleanedAnalysis = analysis
                .replace("```json", "")
                .replace("```", "")
                .trim();

        return objectMapper.readValue(cleanedAnalysis, AdAnalysisResponse.class);
    }


    public record AdAnalysisResponse(
            GoogleAds googleAds,
            BingAds bingAds,
            FacebookAds facebookAds,
            String conclusion
    ) {
    }

    public record GoogleAds(
            String viability,
            String policyCompliance,
            PotentialViolations potentialViolations,
            List<String> keywords,
            String producerRestrictions
    ) {
    }

    public record BingAds(
            String viability,
            String policyCompliance,
            PotentialViolations potentialViolations,
            List<String> keywords,
            String producerRestrictions
    ) {
    }

    public record FacebookAds(
            String viability,
            String policyCompliance,
            PotentialViolations potentialViolations,
            List<String> keywords,
            String producerRestrictions
    ) {
    }

    public record PotentialViolations(
            String misleadingClaims,
            String weightLossClaims,
            String misleadingHealthClaims,
            String exaggeratedClaims,
            String producerRestrictions
    ) {
    }
}