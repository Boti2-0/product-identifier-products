package com.boti.mkdigital.productsidentifier.controller;

import com.boti.mkdigital.productsidentifier.DTO.ClickBankCategoryDTO;
import com.boti.mkdigital.productsidentifier.DTO.ClickBankProductDTO;
import com.boti.mkdigital.productsidentifier.DTO.ClickBankSubcategoryDTO;
import com.boti.mkdigital.productsidentifier.service.CategoryService;
import com.boti.mkdigital.productsidentifier.service.ProductService;
import com.boti.mkdigital.productsidentifier.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductService service;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @GetMapping("")
    public ResponseEntity<Page<ClickBankProductDTO>> getByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) List<Integer> subCategoryIds,
            @RequestParam(required = false) Double gravityInitial,
            @RequestParam(required = false) Double gravityFinal,
            @RequestParam(required = false) Double avgInitial,
            @RequestParam(required = false) Double avgFinal,
            @RequestParam(defaultValue = "true") boolean upsell,
            @RequestParam(defaultValue = "true") boolean rebill,
            @RequestParam(defaultValue = "true") boolean canAdsGoogle,
            @PageableDefault(size = 10, sort = "gravity", direction = ASC) Pageable pageable) {
        Page<ClickBankProductDTO> page = service.getAllProductsAvailableToAdsPageable(
                name,
                categoryIds,
                subCategoryIds,
                gravityInitial,
                gravityFinal,
                avgInitial,
                avgFinal,
                upsell,
                rebill,
                canAdsGoogle,
                pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/category")
    public ResponseEntity<ClickBankCategoryDTO> category() {
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/subcategory")
    public ResponseEntity<ClickBankSubcategoryDTO> subcategory(@RequestHeader(value = "category_id") Integer categoryId) {
        return ResponseEntity.ok().body(subcategoryService.getSubcategoryByCategory(categoryId));
    }

    @GetMapping("/export")
    public ResponseEntity exportLicenses() throws Exception {
        return ResponseEntity
                .ok()
                .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
                .body(service.exportLicenses());
    }

}
