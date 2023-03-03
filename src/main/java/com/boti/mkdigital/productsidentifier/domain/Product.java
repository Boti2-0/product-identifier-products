package com.boti.mkdigital.productsidentifier.domain;

import jakarta.persistence.*;

import com.boti.mkdigital.productsidentifier.DTO.ClickBankHitsResponse;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "produto")
@SequenceGenerator(name = "product_seq_gen", sequenceName = "product_id", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "product_seq_gen")
    private Long id;
    private String site;
    private String title;
    private String description;
    private boolean favorite;
    private String url;
//  marketplaceStats": {
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
//  },
    private String affiliateToolsUrl;
    private String affiliateSupportEmail;
    private String skypeName;

    private boolean canAdsOnGoogle;

    public Product(ClickBankHitsResponse res) {
        this.site = res.getSite();
        this.title = res.getTitle();
        this.description = res.getDescription();
        this.favorite = res.isFavorite();
        this.url = res.getUrl();
        this.activateDate = res.getMarketplaceStats().getActivateDate();
        this.category = res.getMarketplaceStats().getCategory();
        this.subCategory = res.getMarketplaceStats().getSubCategory();
        this.initialDollarsPerSale = res.getMarketplaceStats().getInitialDollarsPerSale();
        this.averageDollarsPerSale = res.getMarketplaceStats().getAverageDollarsPerSale();
        this.gravity = res.getMarketplaceStats().getGravity();
        this.totalRebill = res.getMarketplaceStats().getTotalRebill();
        this.de = res.getMarketplaceStats().isDe();
        this.en = res.getMarketplaceStats().isEn();
        this.es = res.getMarketplaceStats().isEs();
        this.fr = res.getMarketplaceStats().isFr();
        this.it = res.getMarketplaceStats().isIt();
        this.pt = res.getMarketplaceStats().isPt();
        this.standard = res.getMarketplaceStats().isStandard();
        this.physical = res.getMarketplaceStats().isPhysical();
        this.rebill = res.getMarketplaceStats().isRebill();
        this.upsell = res.getMarketplaceStats().isUpsell();
        this.standardUrlPresent = res.getMarketplaceStats().isStandardUrlPresent();
        this.mobileEnabled = res.getMarketplaceStats().isMobileEnabled();
        this.whitelistVendor = res.getMarketplaceStats().isWhitelistVendor();
        this.cpaVisible = res.getMarketplaceStats().isCpaVisible();
        this.dollarTrial = res.getMarketplaceStats().isDollarTrial();
        this.hasAdditionalSiteHoplinks = res.getMarketplaceStats().isHasAdditionalSiteHoplinks();
        this.affiliateToolsUrl = res.getAffiliateToolsUrl();
        this.affiliateSupportEmail = res.getAffiliateSupportEmail();
        this.skypeName = res.getSkypeName();
    }
}


