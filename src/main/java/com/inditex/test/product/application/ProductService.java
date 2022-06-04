package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@RequiredArgsConstructor
public class ProductService implements ProductServiceI
{
    private final ProductDAO productDAO;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override @Transactional
    public Collection<Product>getProducts(int page, int pageSize)
    {   return productDAO.loadProducts(page, pageSize); }

    @Override @Transactional
    public Product getProduct(long productId)
    {   return productDAO.loadProduct(new ProductId(productId)); }

    @Override @Transactional
    public Price assignedPriceFor(long productId, long brandId, long priceListId, LocalDateTime time)
    {
        Product product = productDAO.loadProduct(new ProductId(productId));

        return product.getPrices().getPriceAt(time, new BrandId(brandId), new PriceListId(priceListId));
    }

    @Override @Transactional
    public Price getPrice(long priceId)
    {   return  productDAO.loadPrice(new PriceId(priceId)); }
}