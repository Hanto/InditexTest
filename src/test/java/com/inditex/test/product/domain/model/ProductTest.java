package com.inditex.test.product.domain.model;// Created by jhant on 05/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.inditex.test.product.domain.model.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest
{
    @Nested @DisplayName("WHEN: a product is created")
    class Creation
    {
        @Test @DisplayName("THEN: all fields are coorectly initialized")
        public void test()
        {
            Product product = generateProduct(1L, "short", "long");

            assertThat(product.getProductId()).isEqualTo(new ProductId(1L));
            assertThat(product.getShortName()).isEqualTo("short");
            assertThat(product.getLongName()).isEqualTo("long");
            assertThat(product.getVersion()).isEqualTo(0);
            assertThat(product.getPriceList()).hasSize(0);
        }

        @Test @DisplayName("THEN: name can be changed")
        public void test2()
        {
            Product product = generateProduct(2L, "shortN", "longN");

            product.changeShortName("newShortName");

            assertThat(product.getShortName()).isEqualTo("newShortName");
        }

        @Test @DisplayName("THEN: a new price can be added")
        public void test3()
        {
            Product product = generateProduct(3L, "short", "long");
            Price price     = generatePrice();

            product.addPrice(price);

            assertThat(product.getPriceList()).hasSize(1);
        }

        @Test @DisplayName("THEN: an existing price can be removed")
        public void test4()
        {
            Product product = generateProduct(3L, "short", "long");
            Price price     = generatePrice();

            product.addPrice(price);
            assertThat(product.getPriceList()).hasSize(1);
            product.removePrice(price);
            assertThat(product.getPriceList()).hasSize(0);

        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------------------

    private Product generateProduct(long productIdLong, String shortName, String longName)
    {
        ProductId productId = new ProductId(productIdLong);
        ProductName name    = new ProductName(shortName, longName);
        return new Product(productId, name);
    }

    private Price generatePrice()
    {
        PriceId priceId     = new PriceId(1L);
        BrandId brandId     = new BrandId(1L);
        DateInterval dates  = new DateInterval(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        int priority        = 0;
        Money money         = new Money(100.0f, EUR);

        return new Price(priceId, brandId, dates, priority, money);
    }
}
