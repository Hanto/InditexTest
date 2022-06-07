package com.inditex.test.product.adapter.persistence.mappers;// Created by jhant on 04/06/2022.

import com.inditex.test.product.adapter.persistence.entities.PriceEntity;
import com.inditex.test.product.adapter.persistence.entities.ProductEntity;
import com.inditex.test.product.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
@RequiredArgsConstructor
public class ProductMapper
{
    @Autowired private final PriceMapper priceMapper;

    // FROM ENTITY:
    //--------------------------------------------------------------------------------------------------------

    public Product fromFullEntity(ProductEntity productEntity)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());
        Collection<Price> priceList = productEntity.getPrices().stream().map(priceMapper::fromEntity).toList();

        Product product = new Product(productId, name, new Prices(), productEntity.getVersion());
        product.addPrices(priceList);

        return product;
    }

    public Product fromBaseEntity(ProductEntity productEntity)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());

        return new Product(productId, name, new Prices(), productEntity.getVersion());
    }

    // FROM DOMAIN:
    //--------------------------------------------------------------------------------------------------------

    public ProductEntity fromDomain(Product product)
    {
        List<PriceEntity>priceEntities = product.getPrices().getPriceList().stream()
            .map(priceMapper::fromDomain).toList();
        priceEntities.forEach(price -> price.setProductId(product.getProductId().getId()));

        return ProductEntity.builder()
            .productId(product.getProductId().getId())
            .shortName(product.getProductName().getShortName())
            .longName(product.getProductName().getLongName())
            .prices(priceEntities)
            .version(product.getVersion())
            .build();
    }


}