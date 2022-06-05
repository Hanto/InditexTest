package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value @AllArgsConstructor
public class ProductId
{
    Long id;

    // NEW CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public ProductId()
    {   this.id = UUID.randomUUID().getMostSignificantBits(); }
}
