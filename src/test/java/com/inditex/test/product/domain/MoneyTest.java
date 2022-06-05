package com.inditex.test.product.domain;// Created by jhant on 04/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MoneyTest
{
    @Nested @DisplayName("WHEN: different currencies")
    public class DifferentCurrencies
    {

        @Test @DisplayName("THEN: an exception is thrown")
        public void operationTest()
        {
            Money money1 = new Money(100.50f, "EUR");
            Money money2 = new Money(100.50f, "USD");

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                money1.plus(money2))
                .withMessageContaining("Currency should be the same");

            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                money1.minus(money2))
                .withMessageContaining("Currency should be the same");
        }
    }

    @Nested @DisplayName("WHEN: different currencies")
    public class SameCurrencies
    {

        @Test @DisplayName("THEN: can sum both")
        public void plusTest()
        {
            Money money1 = new Money(100.50f, Currency.EUR);
            Money money2 = new Money(200.50f, Currency.EUR);

            Money result = money1.plus(money2);

            assertThat(result.getCuantity()).isEqualByComparingTo("301");
            assertThat(result.isPositive()).isTrue();
            assertThat(money1.getCuantity()).isEqualByComparingTo("100.50");
            assertThat(money2.getCuantity()).isEqualByComparingTo("200.5");

            assertThat(money1).isNotEqualTo(result);
            assertThat(money2).isNotEqualTo(result);
        }

        @Test @DisplayName("THEN: can substract both")
        public void minusTest()
        {
            Money money1 = new Money(100.50f, Currency.EUR);
            Money money2 = new Money(200.50f, Currency.EUR);

            Money result = money1.minus(money2);

            assertThat(result.getCuantity()).isEqualByComparingTo("-100");
            assertThat(result.isPositive()).isFalse();
            assertThat(money1.getCuantity()).isEqualByComparingTo("100.5");
            assertThat(money2.getCuantity()).isEqualByComparingTo("200.50");

            assertThat(money1).isNotEqualTo(result);
            assertThat(money2).isNotEqualTo(result);
        }
    }
}