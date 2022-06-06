package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RequiredArgsConstructor
public class ProductService implements ProductServiceI
{
    private final PersistenceDAO persistenceDAO;
    private final MemoryDAO memoryDAO;

    // CREATION:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProduct(String shortName, String longName)
    {
        ProductId id    = new ProductId(memoryDAO.generateUniqueProductId());
        ProductName name= new ProductName(shortName, longName);
        Product product = new Product(id, name);

        persistenceDAO.saveProduct(product);
    }

    // SELECT:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<Product>getProducts(int page, int pageSize)
    {   return persistenceDAO.loadProducts(page, pageSize); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product getProduct(long productId)
    {   return persistenceDAO.loadProduct(new ProductId(productId)); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price assignedPriceFor(long productId, long brandId, LocalDateTime time)
    {
        Product product = persistenceDAO.loadProduct(new ProductId(productId));
        return product.getPrices().getPriceAt(time, new BrandId(brandId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price getPrice(long priceId)
    {   return persistenceDAO.loadPrice(new PriceId(priceId)); }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Retryable(
        value = {StaleStateException.class},
        maxAttempts = 4,
        backoff = @Backoff(delay = 50, multiplier = 2, maxDelay = 1000))
    @Transactional(
        propagation = REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void modifyShortName(long priceId, String shortName)
    {
        Product product = persistenceDAO.loadProduct(new ProductId(priceId));
        product.changeShortName(shortName);
        persistenceDAO.saveProduct(product);
    }
}