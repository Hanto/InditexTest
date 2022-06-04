package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProductService implements ProductServiceI
{
    private final ProductDAO productDAO;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Product getProduct(long productId)
    {   return productDAO.loadProduct(new ProductId(productId)); }

    @Override
    public Price assignedPriceFor(long productId, long brandId, long priceListId, LocalDateTime time)
    {
        Product product = productDAO.loadProduct(new ProductId(productId));

        return product.getPrices().getPriceAt(time, new BrandId(brandId), new PriceListId(priceListId));
    }

    @Override
    public Price getPrice(long priceId)
    {   return  productDAO.loadPrice(new PriceId(priceId)); }
}