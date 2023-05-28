package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.SubcategoryDTO;
import com.boti.mkdigital.productsidentifier.domain.Subcategory;
import com.boti.mkdigital.productsidentifier.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubcategoryService {

    private final SubcategoryRepository repository;

    public SubcategoryDTO getSubcategoryByCategory(Integer categoryId){
        List<Subcategory> subcategories = repository.findSubCategoriesByCategoryId(categoryId);
        if(subcategories.isEmpty()){
            log.warn("[SubcategoryService][getSubcategory]- Not found any subcategory for category id:" + categoryId);
            return SubcategoryDTO.builder().build();
        }
        List<String> subcategoryList = new ArrayList<>();
        subcategories.forEach(subcategory -> subcategoryList.add(subcategory.getSubCategory()));
        return SubcategoryDTO.builder().subcategories(subcategoryList).build();
    }
}
