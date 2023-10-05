package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.SubcategoryDTO;
import com.boti.mkdigital.productsidentifier.domain.Subcategory;
import com.boti.mkdigital.productsidentifier.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubcategoryService {

    private final SubcategoryRepository repository;

    public List<SubcategoryDTO> getSubcategoryByCategory(Integer categoryId){
        List<Subcategory> subcategories = repository.findSubCategoriesByCategoryId(categoryId);
return subcategories.stream().map(s -> SubcategoryDTO.builder().id(Math.toIntExact(s.getId())).name(s.getSubCategory()).build()).collect(Collectors.toList());
    }
}
