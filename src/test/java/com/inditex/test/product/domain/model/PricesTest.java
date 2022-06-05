package com.inditex.test.product.domain.model;// Created by jhant on 04/06/2022.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.inditex.test.product.domain.model.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PricesTest
{
    private Prices prices;

    Price price1 = buildPrice(1L, 1L, 1, 1,"2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, EUR, 10.50f);
    Price price2 = buildPrice(2L, 1L, 1, 1,"2020-06-14T00:00:00", "2021-12-31T23:59:59", 0, EUR, 20.50f);
    Price price3 = buildPrice(3L, 1L, 1, 2,"2020-06-14T15:00:00", "2020-06-14T18:30:00", 1, EUR, 30.50f);
    Price price4 = buildPrice(4L, 1L, 1, 3,"2020-06-15T00:00:00", "2020-06-15T11:00:00", 1, EUR, 50.50f);
    List<Price>priceList = Arrays.asList(price1, price2, price3, price4);

    @BeforeEach
    public void beforeAll()
    {
        prices = new Prices();
        prices.addPrices(priceList);
    }

    // ADDING:
    //--------------------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Adding prices")
    class AddPrice
    {
        @Nested @DisplayName("WHEN: Adding existant price")
        class SamePrice
        {
            @Test @DisplayName("THEN: Existant price is not added")
            public void addDuplicateTest()
            {
                assertThat(prices.getPriceList()).hasSize(priceList.size());
                prices.addPrices(List.of(price1));
                prices.addPrices(List.of(price1));
                assertThat(prices.getPriceList()).hasSize(priceList.size());
            }
        }

        @Nested @DisplayName("WHEN: Adding non existant prices")
        class DifferentPrice
        {
            @Test @DisplayName("THEN: prices are added")
            public void addNewPrice()
            {
                Price newPrice1 = buildPrice(10L, 2L, 2, 1,"2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, EUR, 50.50f);
                Price newPrice2 = buildPrice(11L, 2L, 2, 1,"2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, Currency.USD, 50.50f);

                assertThat(prices.getPriceList()).hasSize(priceList.size());
                prices.addPrices(List.of(newPrice1));
                prices.addPrices(List.of(newPrice2));
                assertThat(prices.getPriceList()).hasSize(priceList.size() +2);
            }
        }
    }

    // REMOVING:
    //--------------------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Removing prices")
    class RemovePrice
    {
        @Nested @DisplayName("WHEN: Price exist")
        class PriceExist
        {
            @Test @DisplayName("THEN: prices is removed")
            public void removePriceTest()
            {
                assertThat(prices.getPriceList()).hasSize(priceList.size());
                prices.removePrice(price1);
                assertThat(prices.getPriceList()).hasSize(priceList.size() -1);
            }
        }

        @Nested @DisplayName("WHEN: Price doesn't exist")
        class PriceDoesntExist
        {
            @Test @DisplayName("THEN: nothing is removed")
            public void removePriceTest()
            {
                Price newPrice1 = buildPrice(30L, 2L, 2, 1,"2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, EUR, 50.50f);


                assertThat(prices.getPriceList()).hasSize(priceList.size());
                prices.removePrice(newPrice1);
                assertThat(prices.getPriceList()).hasSize(priceList.size());
            }
        }
    }

    // PRICELIST:
    //--------------------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Directly modifying price list")
    class PriceListUnmodifiable
    {
        @Test @DisplayName("THEN: An Exception is thrown")
        public void priceListIsUnmodifiable()
        {
            Collection<Price>priceList = prices.getPriceList();

            assertThatExceptionOfType(Exception.class).isThrownBy(priceList::clear);
        }
    }

    // GETPRICE:
    //--------------------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Retrieving a price for now")
    class PriceFor
    {
        @Nested @DisplayName("WHEN: A price exist")
        class PriceExists
        {
            @Test @DisplayName("THEN: a price in that time interval with the highest priority is returned")
            public void getPriceNowTest()
            {
                Price priceNow = buildPrice(5L, 1L, 1, 1,LocalDateTime.now().minusSeconds(1).toString(), LocalDateTime.now().plusMinutes(1).toString(), 2, EUR, 50.50f);
                prices.addPrices(List.of(priceNow));
                assertThat(prices.getPriceList()).hasSize(priceList.size() +1);

                Price result = prices.getPriceNow(priceNow.getBrandId(), priceNow.getPriceListId());

                assertThat(result).isEqualTo(priceNow);
                assertThat(result.getMoney()).isEqualTo(priceNow.getMoney());
            }
        }

        @Nested @DisplayName("WHEN: A price doesn't exist")
        class PriceDoesntExist
        {
            @Test @DisplayName("THEN: an exception is thrown")
            public void getPriceAtDoesntExistTest()
            {
                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy( ()->
                    prices.getPriceNow(price2.getBrandId(), price2.getPriceListId()));
            }
        }
    }

    @Nested @DisplayName("WHEN: Retrieving a price for a Date")
    class PriceForDate
    {
        @Nested @DisplayName("WHEN: A price exist")
        class PriceExists
        {
            @Test @DisplayName("THEN: a price in that time interval with the highest priority is returned")
            public void getPriceAtTest()
            {
                Price result = prices.getPriceAt(
                    LocalDateTime.parse("2020-06-14T00:00:01"),
                    price2.getBrandId(),
                    price2.getPriceListId());

                assertThat(result).isEqualTo(price1);
                assertThat(result.getMoney()).isEqualTo(price1.getMoney());

                result = prices.getPriceAt(
                    LocalDateTime.parse("2021-06-14T00:00:01"),
                    price2.getBrandId(),
                    price2.getPriceListId());

                assertThat(result).isEqualTo(price2);
                assertThat(result.getMoney()).isEqualTo(price2.getMoney());
            }
        }

        @Nested @DisplayName("WHEN: A price doesn't exist")
        class PriceDoesntExist
        {
            @Test @DisplayName("THEN: an exception is thrown")
            public void getPriceAtDoesntExistTest()
            {
                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy( ()->
                    prices.getPriceAt(
                        LocalDateTime.parse("2022-06-14T00:00:01"),
                        price2.getBrandId(),
                        price2.getPriceListId()));
            }
        }
    }


    // HELPER:
    //--------------------------------------------------------------------------------------------------------------------

    private Price buildPrice(Long priceL, Long productL, long brandL, long priceListL,
        String startString, String endString, int priority, Currency currency, float quantity)
    {
        PriceId priceId             = new PriceId(priceL);
        ProductId productId         = new ProductId(productL);
        BrandId branId              = new BrandId(brandL);
        PriceListId priceListId     = new PriceListId(priceListL);
        LocalDateTime start         = LocalDateTime.parse(startString);
        LocalDateTime end           = LocalDateTime.parse(endString);
        DateInterval dateInterval   = new DateInterval(start, end);
        Money money                 = new Money(quantity, currency);

        return new Price(priceId, productId, branId, priceListId, dateInterval, priority, money);
    }
}
