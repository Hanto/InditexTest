package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value @AllArgsConstructor
public class PriceId
{
    Long id;

    // NEW CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public PriceId()
    {   this.id = UUID.randomUUID().getLeastSignificantBits(); }
}