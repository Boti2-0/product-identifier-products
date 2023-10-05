package com.boti.mkdigital.productsidentifier.repository.specs;

import com.boti.mkdigital.productsidentifier.domain.Category_;
import com.boti.mkdigital.productsidentifier.domain.Subcategory_;
import com.boti.mkdigital.productsidentifier.domain.Product;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static com.boti.mkdigital.productsidentifier.domain.Product_.*;
import static com.boti.mkdigital.productsidentifier.repository.specs.SpecsUtils.iLikeFormat;

@NoArgsConstructor
public class ProductSpecs {

    public static Specification<Product> nameLike(String v) {
        final String val = iLikeFormat(v);
        return (root, query, cb) -> cb.like(cb.lower(root.get(title)), val);
    }

    public static Specification<Product> rankingEq(String v) {
        return (root, query, cb) -> cb.equal(root.get(ranking), v);
    }

    public static Specification<Product> categoryIdsIn(List<Integer> ids) {
        return (root, query, cb) -> root.get(category).get(Category_.ID).in(ids);
    }

    public static Specification<Product> subCategoryIdsIn(List<Integer> ids) {
        return (root, query, cb) -> root.get(subCategory).get(Subcategory_.ID).in(ids);
    }

    public static Specification<Product> avgGreaterThan(Double val) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(averageDollarsPerSale), val);
    }

    public static Specification<Product> avgLessThan(Double val) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(averageDollarsPerSale), val);
    }

    public static Specification<Product> hasUpsell(boolean value) {
        return (root, query, cb) -> {
            if (value) {
                return cb.equal(root.get(upsell), true);
            } else
                return cb.isNotNull(root.get(id));
        };
    }

    public static Specification<Product> hasRebill(boolean value) {
        return (root, query, cb) -> {
            if (value) {
                return cb.equal(root.get(rebill), true);
            } else
                return cb.isNotNull(root.get(id));
        };
    }

    public static Specification<Product> canAdsOnGoogle(boolean value) {
        return (root, query, cb) -> {
            if (value) {
                return cb.equal(root.get(googleAdsAvailable), true);
            } else
                return cb.isNotNull(root.get(id));
        };
    }

}
