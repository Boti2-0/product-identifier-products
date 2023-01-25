package com.boti.mkdigital.productsidentifier.repository;

import com.boti.mkdigital.productsidentifier.domain.Subcategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends CrudRepository<Subcategory, Integer> {
    Optional<Subcategory> findBySubCategory(String category);
    List<Subcategory> findSubCategoriesByCategoryId(Integer id);

    Optional<Subcategory> findFirstBySubCategory(String subCategory);

    boolean existsByCategoryIdAndSubCategory(Integer id,String subCategory);
}
