package com.boti.mkdigital.productsidentifier.controller;

import com.boti.mkdigital.productsidentifier.DTO.ClickBankProductDTO;
import com.boti.mkdigital.productsidentifier.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService service;
    @GetMapping("/products")
    public ResponseEntity<Page<ClickBankProductDTO>> home(@PageableDefault(size = 20) Pageable pageable) {
        Page<ClickBankProductDTO> page = service.getAllProductsAvailableToAdsPageable(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/export")
    public ResponseEntity exportLicenses() throws Exception {
        return ResponseEntity
                .ok()
                .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
                .body(service.exportLicenses());
    }

}
