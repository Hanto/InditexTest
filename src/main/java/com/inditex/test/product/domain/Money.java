package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value @AllArgsConstructor
public class Money
{
    @NonNull BigDecimal cuantity;
    @NonNull Currency currency;

    // CONSTRUCTORS:
    //--------------------------------------------------------------------------------------------------------

    public Money(BigDecimal cuantity, String currency)
    {
        this.cuantity = cuantity;
        this.currency = Currency.valueOf(currency);
    }

    public Money(Float cuantity, String currency)
    {
        this.cuantity = BigDecimal.valueOf(cuantity);
        this.currency = Currency.valueOf(currency);
    }

    public Money(Float cuantity, Currency currency)
    {
        this.cuantity = BigDecimal.valueOf(cuantity);
        this.currency = currency;
    }

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public Money plus(Money money)
    {
        if (money.getCurrency() != this.getCurrency())
            throw new IllegalArgumentException("Currency should be the same");

        BigDecimal result = this.getCuantity().add(money.getCuantity());

        return new Money(result, this.getCurrency());

    }

    public Money minus(Money money)
    {
        if (money.getCurrency() != this.getCurrency())
            throw new IllegalArgumentException("Currency should be the same");

        BigDecimal result = this.getCuantity().subtract(money.getCuantity());

        return new Money(result, this.getCurrency());
    }

    // CALCULATIONS:
    //--------------------------------------------------------------------------------------------------------

    public boolean isPositive()
    {   return cuantity.compareTo(BigDecimal.ZERO) >= 0; }
}