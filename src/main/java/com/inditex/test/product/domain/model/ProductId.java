package com.inditex.test.product.domain.model;// Created by jhant on 03/06/2022.

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value @AllArgsConstructor
public class ProductId
{
    @NonNull Long id;
}