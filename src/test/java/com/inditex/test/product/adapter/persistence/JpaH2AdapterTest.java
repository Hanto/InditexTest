package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.adapter.persistence.mappers.PriceMapper;
import com.inditex.test.product.adapter.persistence.mappers.ProductMapper;
import com.inditex.test.product.domain.model.*;
import io.sniffy.Sniffy;
import io.sniffy.Spy;
import io.sniffy.boot.EnableSniffy;
import io.sniffy.sql.SqlQueries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DataJpaTest @ActiveProfiles("H2") @EnableSniffy
@Import({JpaH2Adapter.class, ProductMapper.class, PriceMapper.class})
public class JpaH2AdapterTest
{
    @Autowired private JpaH2Adapter adapter;
    private final Spy<?> profiler = Sniffy.spy();

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    @BeforeEach
    public void resetProfiler()
    {   profiler.reset(); }

    // TESTS:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: the database is initialized and queried")
    class Database
    {
        @Test
        @Sql("/test.sql") @DisplayName("THEN: all the products can be retreived")
        public void loadProductsTest()
        {
            Collection<Product> products = adapter.loadProducts(0, 10);

            assertThat(products).hasSize(2);

            profiler.verify(SqlQueries.atMostOneQuery());
        }

        @Test
        @Sql("/test.sql") @DisplayName("THEN: a single p roduct can be retrieved")
        public void loadProductTest()
        {
            Product product = adapter.loadProduct(new ProductId(354550L));

            assertThat(product.getProductId().getId()).isEqualTo(354550L);
            assertThat(product.getProductName().getShortName()).isEqualTo("Black TShirt");
            assertThat(product.getProductName().getLongName()).isEqualTo("Black TShirt from hell");
            assertThat(product.getPrices().getPriceList()).hasSize(5);
            assertThat(product.getPrices().getPriceList()).isUnmodifiable();

            profiler.verify(SqlQueries.atMostOneQuery());
        }

        @Test
        @Sql("/test.sql") @DisplayName("THEN: a single price can be retrieved")
        public void loadPriceTest()
        {
            Price price = adapter.loadPrice(new PriceId(100L));

            assertThat(price.getPriceId().getId()).isEqualTo(100L);
            assertThat(price.getBrandId().getId()).isEqualTo(1L);
            assertThat(price.getDateInterval().getStartDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
            assertThat(price.getDateInterval().getEndDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
            assertThat(price.getPriority()).isEqualTo(1);
            assertThat(price.getMoney().getCuantity()).isEqualTo(new BigDecimal("35.50"));
            assertThat(price.getMoney().getCurrency().toString()).isEqualTo("EUR");

            profiler.verify(SqlQueries.atMostOneQuery());
        }

        @Test
        @Sql("/test.sql") @DisplayName("THEN: a product can be saved")
        public void saveProduct()
        {
            Product product = adapter.loadProduct(new ProductId(354550L));
            product.changeShortName("newShortName");

            assertThatNoException().isThrownBy(() -> adapter.saveProduct(product));
        }

        @Test
        @Sql("/test.sql") @DisplayName("THEN: a new product can be saved")
        public void saveNewProduct()
        {
            Product product = new Product(new ProductId(9999L), new ProductName("short", "long"));
            assertThatNoException().isThrownBy(() -> adapter.saveNewProduct(product));
        }
    }
}