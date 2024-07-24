package com.boti.mkdigital.productsidentifier.controller;

import com.boti.mkdigital.productsidentifier.domain.ProductInfo;
import com.boti.mkdigital.productsidentifier.service.ProductInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-info")
@RequiredArgsConstructor
public class ProductInfoController {
    private final ProductInfoService productInfoService;

    @PostMapping("/revalidate/{productId}")
    public ResponseEntity<Void> revalidateProductInfo(@PathVariable Long productId) {
        productInfoService.revalidateProductInfo(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<ProductInfo> getProductInfoByUrl(@RequestParam String url) {
        ProductInfo productInfo = productInfoService.getProductInfoByUrl(url);
        return ResponseEntity.ok(productInfo);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfo> getProductInfoById(@PathVariable Long productId) {
        ProductInfo productInfo = productInfoService.getProductInfoById(productId);
        return ResponseEntity.ok(productInfo);
    }
}