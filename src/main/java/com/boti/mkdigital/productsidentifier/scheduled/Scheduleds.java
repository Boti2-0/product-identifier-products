package com.boti.mkdigital.productsidentifier.scheduled;


import com.boti.mkdigital.productsidentifier.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduleds {

    private final ProductService service;

//    @Scheduled(fixedDelay = 100000000)
    public void getAllProductsClickBank() throws InterruptedException {
        service.getAllProducts();
        service.updateIfCanGoogleAds();
    }

}