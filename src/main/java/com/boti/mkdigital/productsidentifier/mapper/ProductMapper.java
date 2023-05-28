package com.boti.mkdigital.productsidentifier.mapper;

import com.boti.mkdigital.productsidentifier.DTO.ProductDTO;
import com.boti.mkdigital.productsidentifier.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(source = "category", target = "category.category")
    @Mapping(source = "subCategory", target = "subCategory.subCategory")
    Product toEntity(ProductDTO dto);

    @Mapping(target = "category", source = "category.category")
    @Mapping(target = "subCategory", source = "subCategory.subCategory")
    ProductDTO toDto(Product product);

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

}
