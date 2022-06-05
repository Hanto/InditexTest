package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProductDTOMapper
{
    ProductDTO fromModel(Product model)
    {
        return ProductDTO.builder()
            .productId(model.getProductId().getId())
            .shortName(model.getProductName().getShortName())
            .longName(model.getProductName().getLongName())
            .build();
    }
}