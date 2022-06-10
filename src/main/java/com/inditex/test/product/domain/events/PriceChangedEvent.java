package com.inditex.test.product.domain.events;// Created by jhant on 10/06/2022.

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter @NoArgsConstructor @ToString(callSuper = true)
public class PriceChangedEvent extends DomainEvent implements Serializable
{
    private float oldPrice;
    private float newPrice;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Builder
    public PriceChangedEvent(Long aggregateId, float oldPrice, float newPrice)
    {
        super(aggregateId, PriceChangedEvent.class);
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}