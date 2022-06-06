package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
class PriceDTOMapper
{
    PriceDTO fromModel(Price model)
    {
        return PriceDTO.builder()
            .productId(model.getProductId().getId())
            .priceId(model.getPriceId().getId())
            .brandId(model.getBrandId().getId())
            .startDate(model.getDateInterval().getStartDate())
            .endDate(model.getDateInterval().getEndDate())
            .priority(model.getPriority())
            .currency(model.getMoney().getCurrency().name())
            .price(model.getMoney().getCuantity())
            .build();
    }
}
