package com.inditex.test.product.domain.model;// Created by jhant on 04/06/2022.

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value @AllArgsConstructor
public class PriceListId
{
    @NonNull Long id;
}
