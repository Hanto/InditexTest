package com.inditex.test.product.application.port.in;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceManipulationUseCase
{
    Price getPrice(long priceId);
    Price assignedPriceFor(long productId, long brandId, LocalDateTime time);
    void modifyPrice(ModifyPriceCommand command);
}