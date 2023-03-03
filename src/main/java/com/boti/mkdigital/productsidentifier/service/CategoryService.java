package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.ClickBankCategoryDTO;
import com.boti.mkdigital.productsidentifier.domain.Category;
import com.boti.mkdigital.productsidentifier.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository repository;

    public ClickBankCategoryDTO getAllCategory() {
        List<String> categories = new ArrayList<>();
        Iterable<Category> all = repository.findAll();
        all.forEach(category -> categories.add(category.getCategory()));
        return ClickBankCategoryDTO.builder().category(categories).build();
    }

}
