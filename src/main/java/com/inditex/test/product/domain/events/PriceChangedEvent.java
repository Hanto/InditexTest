package com.inditex.test.product.domain.events;// Created by jhant on 10/06/2022.

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter @NoArgsConstructor @ToString(callSuper = true)
public class PriceChangedEvent extends DomainEvent implements Serializable
{
    private BigDecimal oldPrice;
    private BigDecimal newPrice;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Builder
    public PriceChangedEvent(Long aggregateId, BigDecimal oldPrice, BigDecimal newPrice)
    {
        super(aggregateId, PriceChangedEvent.class);
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}