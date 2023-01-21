package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ClickBankMarketPlanceStatsResponse {
        private LocalDate activateDate;
        private String category;
        private String subCategory;
        private Double initialDollarsPerSale;
        private Double averageDollarsPerSale;
        private Double gravity;
        private Double totalRebill;
        private boolean de;
        private boolean en;
        private boolean es;
        private boolean fr;
        private boolean it;
        private boolean pt;
        private boolean standard;
        private boolean physical;
        private boolean rebill;
        private boolean upsell;
        private boolean standardUrlPresent;
        private boolean mobileEnabled;
        private boolean whitelistVendor;
        private boolean cpaVisible;
        private boolean dollarTrial;
        private boolean hasAdditionalSiteHoplinks;

}


