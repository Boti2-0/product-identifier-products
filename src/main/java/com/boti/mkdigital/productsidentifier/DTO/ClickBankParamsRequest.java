package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ClickBankHitsResponse {
    private String site;
    private String title;
    private String description;
    private boolean favorite;
    private String url;
    private ClickBankMarketplaceStatsResponse marketplaceStats;
    private String affiliateToolsUrl;
    private String affiliateSupportEmail;
    private String skypeName;

}


