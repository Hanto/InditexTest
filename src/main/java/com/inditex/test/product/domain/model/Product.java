package com.inditex.test.product.domain.model;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public class Product
{
    @EqualsAndHashCode.Include
    @Getter private ProductId productId;
    private ProductName productName;
    private final Prices prices;
    @Getter private final int version;

    // CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public Product(ProductId productId, ProductName name)
    {
        this.productId = productId; this.productName = name;
        this.prices = new Prices(); this.version = 0;
    }

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public void addPrice(Price price)
    {   addPrices(List.of(price)); }

    public void addPrices(Collection<Price> collection)
    {   prices.addPrices(collection); }

    public void removePrice(Price price)
    {   this.prices.removePrice(price); }

    public void changeShortName(String shortName)
    {   this.productName = new ProductName(shortName, productName.getLongName()); }

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public String getShortName()
    {   return productName.getShortName(); }

    public String getLongName()
    {   return productName.getLongName(); }

    public Collection<Price> getPriceList()
    {   return prices.getPriceList(); }

    public Price getPriceNow(BrandId brandId)
    {   return prices.getPriceNow(brandId); }

    public Price getPriceAt(LocalDateTime dateTime, BrandId brand)
    {   return prices.getPriceAt(dateTime, brand); }

    public Price getPrice(PriceId priceId)
    {   return prices.getPrice(priceId); }

}