package com.inditex.test.product.adapter.persistence;// Created by jhant on 03/06/2022.

import com.inditex.test.product.application.ProductDAO;
import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.PriceId;
import com.inditex.test.product.domain.Product;
import com.inditex.test.product.domain.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JpaPersistenceAdapter implements ProductDAO
{
    @Autowired private final PriceRepository priceRepo;
    @Autowired private final ProductRepository productRepo;
    @Autowired private final ProductMapper productMapper;
    @Autowired private final PriceMapper priceMapper;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Product loadProduct(ProductId productId)
    {
        ProductEntity productEntity = productRepo.findById(productId.getId())
            .orElseThrow(() -> new IllegalArgumentException(format("No product exists with the id: %s", productId.getId())));

        return productMapper.fromFullEntity(productEntity);
    }

    @Override
    public Collection<Product>loadProducts(int page, int pageSize)
    {
        List<ProductEntity> list = productRepo.findAll(PageRequest.of(0, pageSize)).getContent();

        return list.stream()
            .map(productMapper::fromBaseEntity).toList();
    }

    @Override
    public Price loadPrice(PriceId priceId)
    {
        PriceEntity priceEntity = priceRepo.findById(priceId.getId())
            .orElseThrow(() -> new IllegalArgumentException(format("No product exists with the id: %s", priceId.getId())));

        return priceMapper.fromEntity(priceEntity);
    }
}