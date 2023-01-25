package com.boti.mkdigital.productsidentifier.repository;

import com.boti.mkdigital.productsidentifier.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends
        CrudRepository<Category, Integer> {

    String findCategoryByCategory(String category);
    Optional<Category> findByCategory(String category);


}
