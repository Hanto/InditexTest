package com.inditex.test.product.application.service;// Created by jhant on 07/06/2022.

import com.inditex.test.common.UseCase;
import com.inditex.test.product.application.port.in.PaginationCommand;
import com.inditex.test.product.application.port.in.ProductUseCase;
import com.inditex.test.product.application.port.out.MemoryRepository;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import com.inditex.test.product.domain.model.ProductName;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@UseCase
@RequiredArgsConstructor
public class ProductService implements ProductUseCase
{
    private final PersistenceRepository persistenceRepository;
    private final MemoryRepository memoryRepository;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProduct(ProductName name)
    {
        ProductId id    = new ProductId(memoryRepository.generateUniqueProductId());
        Product product = new Product(id, name);

        persistenceRepository.saveNewProduct(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<Product> getProducts(PaginationCommand command)
    {   return persistenceRepository.loadProducts(command.getPage(), command.getPageSize()); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product getProduct(ProductId productId)
    {   return persistenceRepository.loadProduct(productId); }
}
