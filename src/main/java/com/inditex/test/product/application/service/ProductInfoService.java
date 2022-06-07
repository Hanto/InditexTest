package com.inditex.test.product.application.service;// Created by jhant on 07/06/2022.

import com.inditex.test.product.application.port.in.ProductInfoUseCase;
import com.inditex.test.product.application.port.out.MemoryRepository;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import com.inditex.test.product.domain.model.ProductName;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
public class ProductInfoService implements ProductInfoUseCase
{
    private final PersistenceRepository persistenceRepository;
    private final MemoryRepository memoryRepository;

    // MAIN:
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<Product> getProducts(int page, int pageSize)
    {   return persistenceRepository.loadProducts(page, pageSize); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product getProduct(long productId)
    {   return persistenceRepository.loadProduct(new ProductId(productId)); }
}
