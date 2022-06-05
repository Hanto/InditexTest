package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import io.sniffy.Sniffy;
import io.sniffy.Spy;
import io.sniffy.boot.EnableSniffy;
import io.sniffy.sql.SqlQueries;
import org.junit.jupiter.api.BeforeEach;
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

@DataJpaTest @ActiveProfiles("JpaH2") @EnableSniffy
@Import({JpaPersistenceAdapter.class, ProductMapper.class, PriceMapper.class})
class JpaPersistenceAdapterTest
{
    @Autowired private JpaPersistenceAdapter adapter;
    private final Spy<?> profiler = Sniffy.spy();

    @BeforeEach
    public void resetProfiler()
    {   profiler.reset(); }

    // TESTS:
    //--------------------------------------------------------------------------------------------------------

    @Test
    @Sql("/test.sql")
    public void generateProductIdTest()
    {
        long sequence = adapter.generateUniqueProductId();

        assertThat(sequence).isEqualTo(1);
        sequence = adapter.generateUniqueProductId();
        assertThat(sequence).isEqualTo(2);
        sequence = adapter.generateUniqueProductId();
        assertThat(sequence).isEqualTo(3);

        profiler.verify(SqlQueries.maxQueries(3));
    }

    @Test
    @Sql("/test.sql")
    public void generatePriceIdTest()
    {
        long sequence = adapter.generateUniquePriceId();

        assertThat(sequence).isEqualTo(1);
        sequence = adapter.generateUniquePriceId();
        assertThat(sequence).isEqualTo(2);
        sequence = adapter.generateUniquePriceId();
        assertThat(sequence).isEqualTo(3);

        profiler.verify(SqlQueries.maxQueries(3));
    }

    @Test
    @Sql("/test.sql")
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
    @Sql("/test.sql")
    public void loadProductsTest()
    {
        Collection<Product> products = adapter.loadProducts(0, 10);

        assertThat(products).hasSize(2);

        profiler.verify(SqlQueries.atMostOneQuery());
    }

    @Test
    @Sql("/test.sql")
    public void loadPriceTest()
    {
        Price price = adapter.loadPrice(new PriceId(100L));

        assertThat(price.getPriceId().getId()).isEqualTo(100L);
        assertThat(price.getProductId().getId()).isEqualTo(354550L);
        assertThat(price.getBrandId().getId()).isEqualTo(1L);
        assertThat(price.getPriceListId().getId()).isEqualTo(1);
        assertThat(price.getDateInterval().getStartDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(price.getDateInterval().getEndDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(price.getPriority()).isEqualTo(1);
        assertThat(price.getMoney().getCuantity()).isEqualTo(new BigDecimal("35.50"));
        assertThat(price.getMoney().getCurrency().toString()).isEqualTo("EUR");

        profiler.verify(SqlQueries.atMostOneQuery());
    }
}