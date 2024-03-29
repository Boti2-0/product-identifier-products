package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.CategoryDTO;
import com.boti.mkdigital.productsidentifier.domain.Category;
import com.boti.mkdigital.productsidentifier.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository repository;

    public List<CategoryDTO> getAllCategory() {
        return repository.findAll().stream().map(c -> CategoryDTO.builder().id(c.getId()).name(c.getCategory()).build()).collect(Collectors.toList());
    }

}
