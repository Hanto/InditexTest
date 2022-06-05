package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public class Product
{
    @Getter private ProductId productId;
    @Getter private ProductName productName;
    @Getter private final Prices prices;
    @Getter private final int version;

    // NEW CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public static Product newProduct(ProductName productName)
    {   return new Product(new ProductId(null), productName, new Prices(), 0); }

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

    public void removePrice(PriceId priceId)
    {   this.prices.removePrice(priceId); }

    public void changeShortName(String shortName)
    {   this.productName = new ProductName(shortName, productName.getLongName()); }
}