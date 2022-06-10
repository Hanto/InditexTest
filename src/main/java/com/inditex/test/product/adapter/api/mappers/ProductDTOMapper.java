package com.inditex.test.product.adapter.api.mappers;// Created by jhant on 04/06/2022.

import com.inditex.test.product.adapter.api.dtos.ProductDTO;
import com.inditex.test.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDTOMapper
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