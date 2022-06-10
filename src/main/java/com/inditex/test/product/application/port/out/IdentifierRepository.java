package com.inditex.test.product.application.port.out;// Created by jhant on 06/06/2022.

public interface IdentifierRepository
{
    long generateUniqueProductId();
    long generateUniquePriceId();
}
