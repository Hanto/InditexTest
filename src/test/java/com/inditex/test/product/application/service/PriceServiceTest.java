package com.inditex.test.product.application.service;// Created by jhant on 05/06/2022.

import com.inditex.test.product.application.port.in.PriceUseCase;
import com.inditex.test.product.application.port.out.EventBus;
import com.inditex.test.product.application.port.out.ProductRepository;
import com.inditex.test.product.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.inditex.test.product.domain.model.Currency.EUR;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
@ExtendWith(MockitoExtension.class)
public class PriceServiceTest extends BDDMockito
{
    private final ProductRepository dao = mock(ProductRepository.class);
    private final EventBus eventBus = mock(EventBus.class);
    private final PriceUseCase service = new PriceService(dao, eventBus);

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: the application service: ProductService is used")
    class Service
    {
        @Test @DisplayName("THEN: can retrieve a Price")
        void test4()
        {
            givenAPriceeWithId(1L);

            Price price = service.getPrice(new PriceId(1L));

            assertThat(price.getPriceId().getId()).isEqualTo(1L);
            assertThat(price.getBrandId().getId()).isEqualTo(1L);
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    void givenAPriceeWithId(long id)
    {
        PriceId priceId     = new PriceId(id);
        BrandId brandId     = new BrandId(1L);
        DateInterval dates  = new DateInterval(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        int priority        = 0;
        Money money         = new Money(100.0f, EUR);
        Price price         = new Price(priceId, brandId, dates, priority, money);

        given(dao.loadPrice(priceId))
            .willReturn(price);
    }
}