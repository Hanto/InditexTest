package com.inditex.test.product.application.port.in;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;

public interface PriceManipulationUseCase
{
    Price getPrice(PriceId priceId);
    Price assignedPriceFor(QueryPriceCommand command);
    void modifyPrice(ModifyPriceCommand command);
}