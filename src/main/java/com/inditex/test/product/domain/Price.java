package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public class Price
{
    @Getter
    private final ProductId productId;

    @Getter
    private final PriceId priceId;

    @Getter
    private final BrandId brandId;

    @Getter
    private final PriceListId priceListId;

    @Getter
    private final DateInterval dateInterval;

    @Getter final int priority;

    @Getter final Money money;
}