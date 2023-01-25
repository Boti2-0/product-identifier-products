package com.boti.mkdigital.productsidentifier.repository;

import com.boti.mkdigital.productsidentifier.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends
        CrudRepository<Product, Integer> {
    List<Product> findAllByAffiliateToolsUrlIsNotNull();

    List<Product> findAllByCanAdsOnGoogleOrderByGravityAscAverageDollarsPerSaleDesc(boolean b);

    Page<Product> findAllByCanAdsOnGoogleOrderByGravityAscAverageDollarsPerSaleDesc(boolean b, Pageable page);
}
