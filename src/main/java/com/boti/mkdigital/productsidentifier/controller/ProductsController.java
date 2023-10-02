package com.boti.mkdigital.productsidentifier.controller;

import com.boti.mkdigital.productsidentifier.DTO.CategoryDTO;
import com.boti.mkdigital.productsidentifier.DTO.ProductDTO;
import com.boti.mkdigital.productsidentifier.DTO.SubcategoryDTO;
import com.boti.mkdigital.productsidentifier.mapper.ProductMapper;
import com.boti.mkdigital.productsidentifier.service.CategoryService;
import com.boti.mkdigital.productsidentifier.service.ProductService;
import com.boti.mkdigital.productsidentifier.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductService service;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ProductMapper mapper;

    @GetMapping("")
    public ResponseEntity<Page<ProductDTO>> getByParams(
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
            @PageableDefault(size = 10, sort = "hanking", direction = ASC) Pageable pageable) {
        Page<ProductDTO> page = service.getAllProductsAvailableToAdsPageable(
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
    public ResponseEntity<CategoryDTO> category() {
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/subcategory")
    public ResponseEntity<SubcategoryDTO> subcategory(@RequestHeader(value = "category_id") Integer categoryId) {
        return ResponseEntity.ok().body(subcategoryService.getSubcategoryByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ProductDTO productDTO) {
        service.save(mapper.toEntity(productDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/beforeUpdate/{marketplace}")
    public ResponseEntity beforeUpdate(@PathVariable String marketplace) {
        service.beforeUpdateProducts(marketplace);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/afterUpdate/{marketplace}")
    public ResponseEntity afterUpdate(@PathVariable String marketplace) {
        service.afterUpdateProducts(marketplace);
        return ResponseEntity.ok().build();
    }
}
