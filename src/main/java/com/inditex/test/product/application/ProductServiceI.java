package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.Product;

import java.time.LocalDateTime;

public interface ProductServiceI
{
    Product getProduct(long productId);
    Price getPrice(long priceId);
    Price assignedPriceFor(long productId, long brandId, long priceListId, LocalDateTime time);
}