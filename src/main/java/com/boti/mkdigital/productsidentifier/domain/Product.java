package com.boti.mkdigital.productsidentifier.domain;

import com.boti.mkdigital.productsidentifier.DTO.ProductDTO;
import jakarta.persistence.*;

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
    private Integer id;
    private String site;
    private String key;
    private String title;
    private String description;
    private String url;
    private LocalDate activateDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Subcategory subCategory;
    private Double initialDollarsPerSale;
    private Double averageDollarsPerSale;
    private String ranking;
    private Double totalRebill;
    private boolean standard;
    private boolean physical;
    private boolean rebill;
    private boolean upsell;
    private boolean valid;
    private String affiliateUrl;
    private String marketplace;
    private boolean googleAdsAvailable;

    public Product applyChanges(Product update){
        this.title = update.getTitle();
        this.description = update.getDescription();
        this.activateDate = update.getActivateDate();
        this.initialDollarsPerSale = update.getInitialDollarsPerSale();
        this.averageDollarsPerSale = update.getAverageDollarsPerSale();
        this.ranking = update.getRanking();
        this.totalRebill = update.getTotalRebill();
        this.standard = update.isStandard();
        this.physical = update.isRebill();
        this.rebill = update.isRebill();
        this.upsell = update.isUpsell();
        this.valid = true;
        this.affiliateUrl = update.getAffiliateUrl();
        this.googleAdsAvailable = update.isGoogleAdsAvailable();

        return this;
    }
}


