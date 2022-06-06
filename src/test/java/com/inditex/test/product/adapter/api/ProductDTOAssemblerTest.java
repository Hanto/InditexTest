package com.inditex.test.product.adapter.api;// Created by jhant on 05/06/2022.

import com.inditex.test.product.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;

import java.time.LocalDateTime;
import java.util.List;

import static com.inditex.test.product.domain.model.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
public class ProductDTOAssemblerTest
{
    private final ProductDTOAssembler assembler = new ProductDTOAssembler(new ProductDTOMapper(), new PriceDTOAssembler(new PriceDTOMapper()));

    @Nested @DisplayName("WHEN: a product is assembled into a DTO")
    class ProductAssembler
    {
        @Test @DisplayName("THEN: all the fields are mapped from the Product")
        void test()
        {
            Product product = generateProduct(1L, "short", "long");
            Price price     = generatePrice(1L);
            product.addPrice(price);

            ProductDTO productDTO = assembler.toModel(product);

            assertThat(productDTO.getProductId()).isEqualTo(product.getProductId().getId());
            assertThat(productDTO.getShortName()).isEqualTo(product.getProductName().getShortName());
            assertThat(productDTO.getLongName()).isEqualTo(product.getProductName().getLongName());
            assertThat(productDTO.getPrices()).hasSize(1);

            for (PriceDTO priceDTO: productDTO.getPrices())
            {
                assertThat(priceDTO.getPriceId()).isEqualTo(price.getPriceId().getId());
                assertThat(priceDTO.getProductId()).isEqualTo(price.getProductId().getId());
                assertThat(priceDTO.getBrandId()).isEqualTo(price.getBrandId().getId());
                assertThat(priceDTO.getStartDate()).isEqualTo(price.getDateInterval().getStartDate());
                assertThat(priceDTO.getEndDate()).isEqualTo(price.getDateInterval().getEndDate());
                assertThat(priceDTO.getPriority()).isEqualTo(price.getPriority());
                assertThat(priceDTO.getCurrency()).isEqualTo(price.getMoney().getCurrency().toString());
                assertThat(priceDTO.getPrice()).isEqualByComparingTo(price.getMoney().getCuantity());
            }
        }

        @Test @DisplayName("THEN: HATEOAS links are added")
        void test2()
        {
            Product product = generateProduct(1L, "short", "long");
            Price price     = generatePrice(1L);
            product.addPrice(price);

            ProductDTO productDTO = assembler.toModel(product);

            assertThat(productDTO.getLink("self")).isPresent();

            for (PriceDTO priceDTO: productDTO.getPrices())
            {
                assertThat(priceDTO.getLink("self")).isPresent();
                assertThat(priceDTO.getLink("product")).isPresent();
                assertThat(priceDTO.getLink("brand")).isPresent();
            }
        }
    }

    @Nested @DisplayName("WHEN: multiple products are assembled into a DTO")
    class ProductsAssembler
    {
        @Test @DisplayName("THEN: all the products are transformed")
        void test2()
        {
            Product product1 = generateProduct(1L, "short", "long");
            Price price1     = generatePrice(1L);
            product1.addPrice(price1);

            Product product2= generateProduct(2L, "short", "long");
            Price price2    = generatePrice(2L);
            product2.addPrice(price2);

            CollectionModel<ProductDTO> result = assembler.toCollectionModel(List.of(product1, product2));

            assertThat(result).hasSize(2);
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

    private Price generatePrice(long productIdLong)
    {
        ProductId productId = new ProductId(productIdLong);
        PriceId priceId     = new PriceId(1L);
        BrandId brandId     = new BrandId(1L);
        DateInterval dates  = new DateInterval(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        int priority        = 0;
        Money money         = new Money(100.0f, EUR);

        return new Price(priceId, productId, brandId, dates, priority, money);
    }
}
