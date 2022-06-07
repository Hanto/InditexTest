package com.inditex.test.product.application;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.*;
import com.inditex.test.product.domain.ports.MemoryRepository;
import com.inditex.test.product.domain.ports.PersistenceRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RequiredArgsConstructor
public class TestUseCase implements TestUseCaseI
{
    private final PersistenceRepository persistenceRepository;
    private final MemoryRepository memoryRepository;

    // CREATION:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProduct(String shortName, String longName)
    {
        ProductId id    = new ProductId(memoryRepository.generateUniqueProductId());
        ProductName name= new ProductName(shortName, longName);
        Product product = new Product(id, name);

        persistenceRepository.saveProduct(product);
    }

    // SELECT:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<Product>getProducts(int page, int pageSize)
    {   return persistenceRepository.loadProducts(page, pageSize); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product getProduct(long productId)
    {   return persistenceRepository.loadProduct(new ProductId(productId)); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price assignedPriceFor(long productId, long brandId, LocalDateTime time)
    {
        Product product = persistenceRepository.loadProduct(new ProductId(productId));
        return product.getPrices().getPriceAt(time, new BrandId(brandId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Price getPrice(long priceId)
    {   return persistenceRepository.loadPrice(new PriceId(priceId)); }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    @Retryable(
        value = {StaleStateException.class},
        maxAttempts = 10,
        backoff = @Backoff(delay = 5))
    @Transactional(
        propagation = REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void modifyPrice(long productId, long priceId, float variation, String currency)
    {
        Product product = persistenceRepository.loadProduct(new ProductId(productId));
        Price price = product.getPrices().getPrice(new PriceId(priceId));
        Money money = new Money(variation, currency);

        price.addCost(money);

        persistenceRepository.saveProduct(product);
    }
}