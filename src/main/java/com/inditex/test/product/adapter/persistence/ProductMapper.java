package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
@RequiredArgsConstructor
public class ProductMapper
{
    @Autowired private final PriceMapper priceMapper;

    // MAPPERS:
    //--------------------------------------------------------------------------------------------------------

    Product fromEntity(ProductEntity productEntity)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());

        return new Product(productId, name, new Prices());
    }

    Product fromEntity(ProductEntity productEntity, Collection<PriceEntity> priceEntities)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());
        Collection<Price> priceList = priceEntities.stream().map(priceMapper::fromEntity).toList();

        Product product = new Product(productId, name, new Prices());
        product.addPrices(priceList);

        return product;
    }
}