package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.ProductDTO;
import com.boti.mkdigital.productsidentifier.domain.Category;
import com.boti.mkdigital.productsidentifier.domain.Product;
import com.boti.mkdigital.productsidentifier.domain.Subcategory;
import com.boti.mkdigital.productsidentifier.mapper.ProductMapper;
import com.boti.mkdigital.productsidentifier.repository.CategoryRepository;
import com.boti.mkdigital.productsidentifier.repository.ProductRepository;
import com.boti.mkdigital.productsidentifier.repository.SubcategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.boti.mkdigital.productsidentifier.repository.specs.ProductSpecs.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductMapper mapper;


    @Transactional
    public void beforeUpdateProducts(String marketplace) {
        repository.markAsInvalidByMarketplace(marketplace);
    }

    @Transactional
    public void afterUpdateProducts(String marketplace) {
        repository.deleteAllByMarketplaceAndValidIsFalse(marketplace);
    }

    @Transactional
    public Product save(Product product) {
        Optional<Product> oldProduct = repository.findByKey(product.getKey());
        if (oldProduct.isPresent()) {
            return repository.save(oldProduct.get().applyChanges(product));
        }
        checkHaveCategory(product);
        product.setValid(true);
        return repository.save(product);
    }

    @Transactional
    public void checkHaveCategory(Product product) {
        Optional<Category> byCategory = categoryRepository.findByCategoryAndMarketplace(product.getCategory().getCategory(), product.getMarketplace());
        if (byCategory.isPresent()) {
            product.setCategory(byCategory.get());
        } else {
            product.setCategory(categoryRepository.save(Category.builder().marketplace(product.getMarketplace()).category(product.getCategory().getCategory()).build()));
        }
        Optional<Subcategory> tmpSubCategory = subcategoryRepository.findByCategoryIdAndSubCategory(product.getCategory().getId(), product.getSubCategory().getSubCategory());
        if (tmpSubCategory.isPresent()) {
            product.setSubCategory(tmpSubCategory.get());
        } else {
            product.setSubCategory(subcategoryRepository.save(Subcategory.builder().subCategory(product.getSubCategory().getSubCategory()).category(product.getCategory()).build()));
        }

    }

    public Page<ProductDTO> getAllProductsAvailableToAdsPageable(String name, List<Integer> categoryIds, List<Integer> subCategoryIds, String ranking, Double avgInitial, Double avgFinal, boolean upsell, boolean rebill, boolean canAdsGoogle, Pageable page) {
        Specification<Product> spec = Specification.where(canAdsOnGoogle(canAdsGoogle).and(hasUpsell(upsell)).and(hasRebill(rebill)));
        spec = Objects.nonNull(name) && !Strings.isBlank(name)? spec.and(nameLike(name)) : spec;
        spec = Objects.nonNull(ranking) && !Strings.isBlank(ranking) ? spec.and(rankingEq(ranking)) : spec;
        spec = Objects.nonNull(categoryIds) && categoryIds.size() > 0? spec.and(categoryIdsIn(categoryIds)) : spec;
        spec = Objects.nonNull(subCategoryIds) &&  subCategoryIds.size() > 0? spec.and(subCategoryIdsIn(subCategoryIds)) : spec;
//        spec = Objects.nonNull(gravityInitial)? spec.and(gravityGreaterThan(gravityInitial)) : spec;
//        spec = Objects.nonNull(gravityFinal)? spec.and(gravityLessThan(gravityFinal)) : spec;
        spec = Objects.nonNull(avgInitial) ? spec.and(avgGreaterThan(avgInitial)) : spec;
        spec = Objects.nonNull(avgFinal) ? spec.and(avgLessThan(avgFinal)) : spec;

        return repository.findAll(spec, page).map(mapper::toDto);
    }

    public List<ProductDTO> getAllProductsAvailableToAds() {
        return mapper.toDto(repository.findAll());
    }

}