package com.inditex.test.product.adapter.persistence.mappers;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.persistence.entities.PriceEntity;
import com.inditex.test.product.domain.model.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
public class PriceMapper
{
    // FROM ENTITY:
    //--------------------------------------------------------------------------------------------------------

    public Price fromEntity(PriceEntity entity)
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

    public PriceEntity fromDomain(Price price)
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
