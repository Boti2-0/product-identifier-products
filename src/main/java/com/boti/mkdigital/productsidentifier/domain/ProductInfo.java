package com.boti.mkdigital.productsidentifier.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private double averageSale;
    private String gravityRange;
    private boolean upsellAvailable;
    private String affiliateLink;
    private boolean googleAdsAvailable;
    private boolean bingAdsAvailable;
    private boolean facebookAdsAvailable;

    @ElementCollection
    @CollectionTable(name = "product_info_google_keywords", joinColumns = @JoinColumn(name = "product_info_id"))
    @Column(name = "keyword")
    private List<String> googleKeywords;

    @ElementCollection
    @CollectionTable(name = "product_info_bing_keywords", joinColumns = @JoinColumn(name = "product_info_id"))
    @Column(name = "keyword")
    private List<String> bingKeywords;

    @ElementCollection
    @CollectionTable(name = "product_info_facebook_keywords", joinColumns = @JoinColumn(name = "product_info_id"))
    @Column(name = "keyword")
    private List<String> facebookKeywords;

    private String googleProducerRestrictions;
    private String bingProducerRestrictions;
    private String facebookProducerRestrictions;

    @OneToOne(mappedBy = "productInfo", cascade = CascadeType.ALL)
    private Product product;
}