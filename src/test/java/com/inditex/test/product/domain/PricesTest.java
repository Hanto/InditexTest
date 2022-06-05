package com.inditex.test.product.domain;// Created by jhant on 04/06/2022.

import java.time.LocalDateTime;

public class PricesTest
{
    public void test()
    {
        Price price1 = buildPrice(1, 1, 1, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 100.50f);
        Price price2 = buildPrice(1, 1, 1, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 100.50f);
        Price price3 = buildPrice(1, 1, 1, 1,
            LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), 1, Currency.EUR, 100.50f);
    }


    // HELPER:
    //--------------------------------------------------------------------------------------------------------------------

    private Price buildPrice(long priceL, long productL, long brandL, long priceListL,
        LocalDateTime start, LocalDateTime end, int priority, Currency currency, float quantity)
    {
        PriceId priceId             = new PriceId(priceL);
        ProductId productId         = new ProductId(productL);
        BrandId branId              = new BrandId(brandL);
        PriceListId priceListId     = new PriceListId(priceL);
        DateInterval dateInterval   = new DateInterval(start, end);
        Money money                 = new Money(quantity, currency);

        return new Price(priceId, productId, branId, priceListId, dateInterval, priority, money);
    }
}
