package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.PriceId;
import com.inditex.test.product.domain.Product;
import com.inditex.test.product.domain.ProductId;

public interface ProductDAO
{
    Product loadProduct(ProductId productId);
    Price loadPrice(PriceId priceId);
}
