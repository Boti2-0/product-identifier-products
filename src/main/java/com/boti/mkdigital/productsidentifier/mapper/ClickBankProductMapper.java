package com.boti.mkdigital.productsidentifier.mapper;

import com.boti.mkdigital.productsidentifier.DTO.ClickBankProductDTO;
import com.boti.mkdigital.productsidentifier.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClickBankProductMapper extends EntityMapper<ClickBankProductDTO, Product> {
    ClickBankProductMapper INSTANCE = Mappers.getMapper(ClickBankProductMapper.class);

}
