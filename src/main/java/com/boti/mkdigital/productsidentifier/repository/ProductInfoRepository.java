package com.boti.mkdigital.productsidentifier.repository;

import com.boti.mkdigital.productsidentifier.domain.Product;
import com.boti.mkdigital.productsidentifier.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInfoRepository
        extends
        CrudRepository<ProductInfo, Long>,
        JpaSpecificationExecutor<ProductInfo> {
}
