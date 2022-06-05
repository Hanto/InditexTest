package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public class Price
{
    @Getter private PriceId priceId;
    @Getter private ProductId productId;
    @Getter private BrandId brandId;
    @Getter private PriceListId priceListId;

    @Getter private DateInterval dateInterval;
    @Getter int priority;
    @Getter Money money;

    // NEW CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public static Price newProduct(ProductId productId, BrandId brandId, PriceListId priceListId,
        DateInterval dateInterval, int priority, Money money)
    {   return new Price(new PriceId(null), productId, brandId, priceListId, dateInterval, priority, money); }
}