package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
@RequiredArgsConstructor
class ProductMapper
{
    // FROM ENTITY:
    //--------------------------------------------------------------------------------------------------------

    Product fromFullEntity(ProductEntity productEntity)
    {
        ProductId productId         = new ProductId(productEntity.getProductId());
        ProductName name            = new ProductName(productEntity.getShortName(), productEntity.getLongName());
        Collection<Price> priceList = productEntity.getPrices().stream().map(this::fromEntity).toList();

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

    Price fromEntity(PriceEntity entity)
    {
        PriceId priceId             = new PriceId(entity.getPriceId());
        BrandId branId              = new BrandId(entity.getBrandId());
        DateInterval dateInterval   = new DateInterval(entity.getStartDate(), entity.getEndDate());
        int priority                = entity.getPriority();
        Money money                 = new Money(entity.getMoney(), entity.getCurrency());

        return new Price(priceId, branId, dateInterval, priority, money, entity.getVersion());
    }

    // FROM DOMAIN:
    //--------------------------------------------------------------------------------------------------------

    ProductEntity fromDomain(Product product)
    {
        List<PriceEntity>priceEntities = product.getPrices().getPriceList().stream()
            .map(this::fromDomain).toList();
        priceEntities.forEach(price -> price.setProductId(product.getProductId().getId()));

        return ProductEntity.builder()
            .productId(product.getProductId().getId())
            .shortName(product.getProductName().getShortName())
            .longName(product.getProductName().getLongName())
            .prices(priceEntities)
            .version(product.getVersion())
            .build();
    }

    PriceEntity fromDomain(Price price)
    {
        return PriceEntity.builder()
            .priceId(price.getPriceId().getId())
            .brandId(price.getBrandId().getId())
            .startDate(price.getDateInterval().getStartDate())
            .endDate(price.getDateInterval().getEndDate())
            .priority(price.getPriority())
            .money(price.getMoney().getCuantity())
            .currency(price.getMoney().getCurrency().name())
            .version(price.getVersion())
            .build();
    }
}