package com.inditex.test.product.domain.model;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public class Price
{
    @EqualsAndHashCode.Include
    @Getter private PriceId priceId;
    @Getter private BrandId brandId;

    @Getter private DateInterval dateInterval;
    @Getter int priority;
    @Getter Money money;

    @Getter private final int version;

    // CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public Price(PriceId priceId, BrandId brandId, DateInterval dateInterval, int priority, Money money)
    {
        this.priceId = priceId; this.brandId = brandId; this.dateInterval = dateInterval;
        this.priority = priority; this.money = money; this.version = 0;
    }

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public void addCost(Money money)
    {   this.money = this.money.plus(money); }
}