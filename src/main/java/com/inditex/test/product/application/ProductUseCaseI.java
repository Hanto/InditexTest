package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.Product;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ProductUseCaseI
{
    void createProduct(String shortName, String longName);

    Collection<Product>getProducts(int page, int pageSize);
    Product getProduct(long productId);
    Price getPrice(long priceId);
    Price assignedPriceFor(long productId, long brandId, LocalDateTime time);

    void modifyPrice(long productId, long priceId, float variation, String currency);
}