package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProductDTO {

    private String site;
    private String key;
    private String title;
    private String description;
    private String url;
    private LocalDate activateDate;
    private String category;
    private String subCategory;
    private Double initialDollarsPerSale;
    private Double averageDollarsPerSale;
    private String ranking;
    private Double totalRebill;
    private boolean standard;
    private boolean physical;
    private boolean rebill;
    private boolean upsell;
    private String affiliateUrl;
    private String marketplace;
    private boolean googleAdsAvailable;
}
