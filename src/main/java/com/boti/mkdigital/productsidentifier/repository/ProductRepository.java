package com.boti.mkdigital.productsidentifier.repository;

import com.boti.mkdigital.productsidentifier.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository
        extends
        CrudRepository<Product, Integer>,
        JpaSpecificationExecutor<Product> {
    List<Product> findAllByAffiliateUrlIsNotNull();
    List<Product> findAll();

    Optional<Product> findByKey(String key);

    @Modifying
    @Query(
            value = " UPDATE product SET valid = false WHERE marketplace = :marketplace",
            nativeQuery = true
    )
    void markAsInvalidByMarketplace(String marketplace);

    @Modifying
    @Query(
            value = " delete from product where valid = false and marketplace = :marketplace",
            nativeQuery = true
    )
    void deleteAllByMarketplaceAndValidIsFalse(String marketplace);
}
