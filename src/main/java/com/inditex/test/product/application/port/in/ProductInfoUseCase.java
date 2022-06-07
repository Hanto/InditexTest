package com.inditex.test.product.application.port.in;// Created by jhant on 07/06/2022.

import com.inditex.test.product.domain.model.Product;

import java.util.Collection;

public interface ProductInfoUseCase
{
    void createProduct(String shortName, String longName);
    Collection<Product> getProducts(int page, int pageSize);
    Product getProduct(long productId);
}
