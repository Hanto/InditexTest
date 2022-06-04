package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public class Product
{
    @Getter
    private final ProductId productId;

    @Getter
    private final ProductName productName;

    @Getter
    private final Prices prices;

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public void addPrice(Price price)
    {   addPrices(List.of(price)); }

    public void addPrices(Collection<Price> collection)
    {
        if  (collection.stream().anyMatch(price -> !price.getProductId().equals(productId)))
            throw new IllegalArgumentException("Cannot add prices from a different productId");

        prices.addPrices(collection);
    }
}