package com.boti.mkdigital.productsidentifier.repository;

import com.boti.mkdigital.productsidentifier.domain.Category;
import com.boti.mkdigital.productsidentifier.domain.Subcategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends CrudRepository<Subcategory, Integer> {
    Subcategory findBySubCategory(String category);
    List<Subcategory> findSubCategoriesByCategoryId(Integer id);
}
