package com.inditex.test.product.domain.services;// Created by jhant on 06/06/2022.

public interface MemoryRepository
{
    long generateUniqueProductId();
    long generateUniquePriceId();
}