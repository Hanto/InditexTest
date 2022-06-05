package com.inditex.test.product.domain;// Created by jhant on 04/06/2022.

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value @AllArgsConstructor
public class BrandId
{
    Long id;

    // NEW CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public BrandId()
    {   this.id = UUID.randomUUID().getLeastSignificantBits(); }
}