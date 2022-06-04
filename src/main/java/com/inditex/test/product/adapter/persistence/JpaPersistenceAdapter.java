package com.inditex.test.product.adapter.persistence;// Created by jhant on 03/06/2022.

import com.inditex.test.product.application.ProductDAO;
import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.PriceId;
import com.inditex.test.product.domain.Product;
import com.inditex.test.product.domain.ProductId;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

        List<PriceEntity>priceEntities = priceRepo.findByProductId(productId.getId());

        return productMapper.fromEntity(productEntity, priceEntities);
    }

    private static final int DEFAULT_PAGESIZE = 10;

    @Override
    public Collection<Product>loadProducts(int page, int pageSize)
    {
        List<Product> products = productRepo.findAll(PageRequest.of(0, pageSize)).stream()
            .map(productMapper::fromEntity).toList();

        for (Product product : products)
        {
            List<Price>priceList = priceRepo.findByProductId(product.getProductId().getId()).stream()
                .map(priceMapper::fromEntity).toList();

            product.addPrices(priceList);
        }

        return products;
    }


    @Override
    public Price loadPrice(PriceId priceId)
    {
        PriceEntity priceEntity = priceRepo.findById(priceId.getId())
            .orElseThrow(() -> new IllegalArgumentException(format("No product exists with the id: %s", priceId.getId())));

        return priceMapper.fromEntity(priceEntity);
    }

}