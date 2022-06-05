package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest @ActiveProfiles("JpaH2")
@Import({JpaPersistenceAdapter.class, ProductMapper.class, PriceMapper.class})
class JpaPersistenceAdapterTest
{
    @Autowired private JpaPersistenceAdapter adapter;

    // TESTS:
    //--------------------------------------------------------------------------------------------------------

    @Test
    @Sql("/test.sql")
    public void loadTest()
    {
        Product product = adapter.loadProduct(new ProductId(35456L));

        assertThat(product.getProductId().getId()).isEqualTo(35456L);
        assertThat(product.getPrices().getPriceList()).hasSize(4);
    }
}