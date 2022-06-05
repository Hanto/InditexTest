package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
@RequiredArgsConstructor
class ProductMapper
{
    @Autowired private final PriceMapper priceMapper;

    // MAPPERS:
    //--------------------------------------------------------------------------------------------------------

    Product fromFullEntity(ProductEntity productEntity)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());
        Collection<Price> priceList = productEntity.getPrices().stream().map(priceMapper::fromEntity).toList();

        Product product = new Product(productId, name, new Prices(), productEntity.getVersion());
        product.addPrices(priceList);

        return product;
    }

    Product fromBaseEntity(ProductEntity productEntity)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());

        return new Product(productId, name, new Prices(), productEntity.getVersion());
    }

    ProductEntity fromDomain(Product product)
    {
        List<PriceEntity>priceEntities = product.getPrices().getPriceList().stream()
            .map(priceMapper::fromDomain).toList();

        return ProductEntity.builder()
            .productId(product.getProductId().getId())
            .shortName(product.getProductName().getShortName())
            .longName(product.getProductName().getLongName())
            .prices(priceEntities)
            .version(product.getVersion())
            .build();
    }
}