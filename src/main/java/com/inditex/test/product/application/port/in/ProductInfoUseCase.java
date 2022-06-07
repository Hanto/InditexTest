package com.inditex.test.product.application.port.in;// Created by jhant on 07/06/2022.

import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import com.inditex.test.product.domain.model.ProductName;

import java.util.Collection;

public interface ProductInfoUseCase
{
    void createProduct(ProductName command);
    Collection<Product> getProducts(PaginationCommand command);
    Product getProduct(ProductId productId);
}
