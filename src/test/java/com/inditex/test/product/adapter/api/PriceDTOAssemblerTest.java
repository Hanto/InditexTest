package com.inditex.test.product.adapter.api;// Created by jhant on 05/06/2022.

import com.inditex.test.product.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.inditex.test.product.domain.model.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
public class PriceDTOAssemblerTest
{
    private final PriceDTOAssembler assembler = new PriceDTOAssembler(new PriceDTOMapper());

    @Nested @DisplayName("WHEN: a price is assembled into a DTO")
    class PriceAssembler
    {
        @Test @DisplayName("THEN: all the fields are mapped from the Price")
        void test()
        {
            Price price = generatePrice(1L);

            PriceDTO priceDTO = assembler.toModel(price);

            assertThat(priceDTO.getPriceId()).isEqualTo(price.getPriceId().getId());
            assertThat(priceDTO.getProductId()).isEqualTo(price.getProductId().getId());
            assertThat(priceDTO.getBrandId()).isEqualTo(price.getBrandId().getId());
            assertThat(priceDTO.getPriceListId()).isEqualTo(price.getPriceListId().getId());
            assertThat(priceDTO.getStartDate()).isEqualTo(price.getDateInterval().getStartDate());
            assertThat(priceDTO.getEndDate()).isEqualTo(price.getDateInterval().getEndDate());
            assertThat(priceDTO.getPriority()).isEqualTo(price.getPriority());
            assertThat(priceDTO.getCurrency()).isEqualTo(price.getMoney().getCurrency().toString());
            assertThat(priceDTO.getPrice()).isEqualByComparingTo(price.getMoney().getCuantity());
        }

        @Test @DisplayName("THEN: HATEOAS links are added")
        void test2()
        {
            Price price = generatePrice(1L);

            PriceDTO priceDTO = assembler.toModel(price);

            assertThat(priceDTO.getLink("self")).isPresent();
            assertThat(priceDTO.getLink("product")).isPresent();
            assertThat(priceDTO.getLink("brand")).isPresent();
            assertThat(priceDTO.getLink("priceList")).isPresent();
        }

    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------------------

    private Price generatePrice(long productIdLong)
    {
        ProductId productId = new ProductId(productIdLong);
        PriceId priceId     = new PriceId(1L);
        BrandId brandId     = new BrandId(1L);
        PriceListId priceLId= new PriceListId(1L);
        DateInterval dates  = new DateInterval(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        int priority        = 0;
        Money money         = new Money(100.0f, EUR);

        return new Price(priceId, productId, brandId, priceLId, dates, priority, money);
    }
}
