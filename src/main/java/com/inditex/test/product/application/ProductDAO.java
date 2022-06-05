package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.PriceId;
import com.inditex.test.product.domain.Product;
import com.inditex.test.product.domain.ProductId;

import java.util.Collection;

public interface ProductDAO
{
    Collection<Product> loadProducts(int page, int pageSize);
    Product loadProduct(ProductId productId);
    Price loadPrice(PriceId priceId);

    void saveProduct(Product product);
}
