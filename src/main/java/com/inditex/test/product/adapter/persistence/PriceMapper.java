package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
class PriceMapper
{
    // MAPPERS:
    //--------------------------------------------------------------------------------------------------------

    Price fromEntity(PriceEntity entity)
    {
        PriceId priceId             = new PriceId(entity.getPriceId());
        ProductId productId         = new ProductId(entity.getProductId());
        BrandId branId              = new BrandId(entity.getBrandId());
        PriceListId priceListId     = new PriceListId(entity.getPriceListId());
        DateInterval dateInterval   = new DateInterval(entity.getStartDate(), entity.getEndDate());
        int priority                = entity.getPriority();
        Money money                 = new Money(entity.getMoney(), entity.getCurrency());

        return new Price(priceId, productId, branId, priceListId, dateInterval, priority, money);
    }

    PriceEntity fromDomain(Price price)
    {
        return PriceEntity.builder()
            .priceId(price.getPriceId().getId())
            .productId(price.getProductId().getId())
            .brandId(price.getBrandId().getId())
            .priceListId(price.getPriceListId().getId())
            .startDate(price.getDateInterval().getStartDate())
            .endDate(price.getDateInterval().getEndDate())
            .priority(price.getPriority())
            .money(price.getMoney().getCuantity())
            .currency(price.getMoney().getCurrency().name())
            .build();
    }
}