package com.inditex.test.product.domain;// Created by jhant on 04/06/2022.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PricesTest
{
    private Prices prices;

    Price price1 = buildPrice(1L, 1L, 1, 1,
        LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 10.50f);
    Price price2 = buildPrice(2L, 1L, 1, 2,
        LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 2, Currency.EUR, 20.50f);
    Price price3 = buildPrice(3L, 1L, 1, 2,
        LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 30.50f);
    Price price4 = buildPrice(4L, 2L, 2, 1,
        LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 50.50f);

    @BeforeEach
    public void beforeAll()
    {
        prices = new Prices();
        prices.addPrices(Arrays.asList(price1, price2, price3, price4));
    }

    // TESTS:
    //--------------------------------------------------------------------------------------------------------------------

    @Test
    public void addPriceTest()
    {
        Price newPrice = buildPrice(5L, 2L, 2, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        assertThat(prices.getPriceList()).hasSize(5);
    }

    @Test
    public void addDuplicateTest()
    {
        Price newPrice = buildPrice(5L, 2L, 2, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        prices.addPrices(List.of(newPrice));
        assertThat(prices.getPriceList()).hasSize(5);
    }

    @Test
    public void addNewPrice()
    {
        Price newPrice = buildPrice(null, 2L, 2, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        prices.addPrices(List.of(newPrice));
        assertThat(prices.getPriceList()).hasSize(5);
    }

    @Test
    public void addNewPrice2()
    {
        Price newPrice = buildPrice(null, 2L, 2, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 50.50f);

        Price newPrice2 = buildPrice(null, 2L, 2, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        prices.addPrices(List.of(newPrice2));
        assertThat(prices.getPriceList()).hasSize(6);
    }

    @Test
    public void removePriceTest()
    {
        assertThat(prices.getPriceList()).hasSize(4);
        prices.removePrice(price1);
        assertThat(prices.getPriceList()).hasSize(3);
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------------------

    private Price buildPrice(Long priceL, Long productL, long brandL, long priceListL,
        LocalDateTime start, LocalDateTime end, int priority, Currency currency, float quantity)
    {
        PriceId priceId             = priceL != null ? new PriceId(priceL) : new PriceId();
        ProductId productId         = productL != null ? new ProductId(productL) : new ProductId();
        BrandId branId              = new BrandId(brandL);
        PriceListId priceListId     = new PriceListId(priceListL);
        DateInterval dateInterval   = new DateInterval(start, end);
        Money money                 = new Money(quantity, currency);

        return new Price(priceId, productId, branId, priceListId, dateInterval, priority, money);
    }
}
