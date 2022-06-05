package com.inditex.test.product.domain;// Created by jhant on 04/06/2022.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.inditex.test.product.domain.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PricesTest
{
    private Prices prices;

    Price price1 = buildPrice(1L, 1L, 1, 1,"2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, EUR, 10.50f);
    Price price2 = buildPrice(2L, 1L, 1, 1,"2020-06-14T00:00:00", "2021-12-31T23:59:59", 0, EUR, 20.50f);
    Price price3 = buildPrice(3L, 1L, 1, 2,"2020-06-14T15:00:00", "2020-06-14T18:30:00", 1, EUR, 30.50f);
    Price price4 = buildPrice(4L, 1L, 1, 3,"2020-06-15T00:00:00", "2020-06-15T11:00:00", 1, EUR, 50.50f);

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
            "2020-06-14T00:00:00", "2020-12-31T23:59:59" , 1, EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        assertThat(prices.getPriceList()).hasSize(5);
    }

    @Test
    public void addDuplicateTest()
    {
        Price newPrice = buildPrice(5L, 2L, 2, 1,
            "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        prices.addPrices(List.of(newPrice));
        assertThat(prices.getPriceList()).hasSize(5);
    }

    @Test
    public void addNewPrice()
    {
        Price newPrice = buildPrice(null, 2L, 2, 1,
            "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, EUR, 50.50f);

        assertThat(prices.getPriceList()).hasSize(4);
        prices.addPrices(List.of(newPrice));
        prices.addPrices(List.of(newPrice));
        assertThat(prices.getPriceList()).hasSize(5);
    }

    @Test
    public void addNewPrice2()
    {
        Price newPrice = buildPrice(null, 2L, 2, 1,
            "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, Currency.USD, 50.50f);

        Price newPrice2 = buildPrice(null, 2L, 2, 1,
            "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, Currency.USD, 50.50f);

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

    @Test
    public void priceListIsUnmodifiable()
    {
        assertThat(prices.getPriceList()).hasSize(4);
        Collection<Price>priceList = prices.getPriceList();

        assertThatExceptionOfType(Exception.class).isThrownBy(priceList::clear);
    }

    @Test
    public void getPriceAtTest()
    {
        Price result = prices.getPriceAt(
            LocalDateTime.parse("2020-06-14T00:00:01"),
            price2.getBrandId(),
            price2.getPriceListId());

        assertThat(result).isEqualTo(price1);

        result = prices.getPriceAt(
            LocalDateTime.parse("2021-06-14T00:00:01"),
            price2.getBrandId(),
            price2.getPriceListId());

        assertThat(result).isEqualTo(price2);
    }

    @Test
    public void getPriceAtDoesntExistTest()
    {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy( ()->
            prices.getPriceAt(
                LocalDateTime.parse("2022-06-14T00:00:01"),
                price2.getBrandId(),
                price2.getPriceListId()));
    }


    // HELPER:
    //--------------------------------------------------------------------------------------------------------------------

    private Price buildPrice(Long priceL, Long productL, long brandL, long priceListL,
        String startString, String endString, int priority, Currency currency, float quantity)
    {
        PriceId priceId             = priceL != null ? new PriceId(priceL) : new PriceId();
        ProductId productId         = productL != null ? new ProductId(productL) : new ProductId();
        BrandId branId              = new BrandId(brandL);
        PriceListId priceListId     = new PriceListId(priceListL);
        LocalDateTime start         = LocalDateTime.parse(startString);
        LocalDateTime end           = LocalDateTime.parse(endString);
        DateInterval dateInterval   = new DateInterval(start, end);
        Money money                 = new Money(quantity, currency);

        return new Price(priceId, productId, branId, priceListId, dateInterval, priority, money);
    }
}
