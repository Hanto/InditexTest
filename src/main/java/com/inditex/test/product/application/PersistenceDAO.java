package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;

import java.util.Collection;

public interface PersistenceDAO
{
    Collection<Product> loadProducts(int page, int pageSize);
    Product loadProduct(ProductId productId);
    Price loadPrice(PriceId priceId);

    void saveProduct(Product product);
}
