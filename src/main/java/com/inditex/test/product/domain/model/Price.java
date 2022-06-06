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
    @Getter private ProductId productId;
    @Getter private BrandId brandId;

    @Getter private DateInterval dateInterval;
    @Getter int priority;
    @Getter Money money;
}