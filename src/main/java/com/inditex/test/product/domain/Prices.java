package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;

@NoArgsConstructor @ToString
public class Prices
{
    private final List<Price>prices = new ArrayList<>();

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    void addPrices(Collection<Price> collection)
    {   prices.addAll(collection); }

    public List<Price> getPriceList()
    {   return Collections.unmodifiableList(prices); }

    public Price getPriceNow(BrandId brand, PriceListId priceList)
    {   return getPriceAt(LocalDateTime.now(), brand, priceList); }

    public Price getPriceAt(LocalDateTime dateTime, BrandId brand, PriceListId priceList)
    {
        return prices.stream()
            .filter(price -> price.getBrandId().equals(brand))
            .filter(price -> price.getPriceListId().equals(priceList))
            .filter(price -> price.getDateInterval().isDateInTheInterval(dateTime))
            .max(Comparator.comparing(Price::getPriority))
            .orElseThrow(() -> new IllegalArgumentException(
                format("There are no prices for the requested parameters, date: %s brand: %s priceList: %s",
                    dateTime, brand.getId(), priceList.getId())));
    }
}