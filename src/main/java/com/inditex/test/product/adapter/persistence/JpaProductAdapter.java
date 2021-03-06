package com.inditex.test.product.adapter.persistence;// Created by jhant on 03/06/2022.

import com.inditex.test.product.adapter.persistence.entities.JpaPriceRepository;
import com.inditex.test.product.adapter.persistence.entities.JpaProductRepository;
import com.inditex.test.product.adapter.persistence.entities.PriceEntity;
import com.inditex.test.product.adapter.persistence.entities.ProductEntity;
import com.inditex.test.product.adapter.persistence.mappers.PriceMapper;
import com.inditex.test.product.adapter.persistence.mappers.ProductMapper;
import com.inditex.test.product.application.port.out.ProductRepository;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs.named;
import static com.inditex.test.product.adapter.persistence.entities.ProductEntity.GRAPH_PRODUCT_ALL;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JpaProductAdapter implements ProductRepository
{
    @Autowired private final JpaPriceRepository priceRepo;
    @Autowired private final JpaProductRepository productRepo;
    @Autowired private final ProductMapper productMapper;
    @Autowired private final PriceMapper priceMapper;

    // PRODUCTS:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Product loadProduct(ProductId productId)
    {
        ProductEntity productEntity = productRepo.findById(productId.getId(), named(GRAPH_PRODUCT_ALL))
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
    public void saveProduct(Product product)
    {
        ProductEntity entity = productMapper.fromDomain(product);
        productRepo.save(entity);
    }

    @Override
    public void saveNewProduct(Product product)
    {
        ProductEntity entity = productMapper.fromDomain(product);
        entity.setNew(true);
        productRepo.save(entity);
    }

    // PRICES:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Price loadPrice(PriceId priceId)
    {
        PriceEntity priceEntity = priceRepo.findById(priceId.getId())
            .orElseThrow(() -> new IllegalArgumentException(format("No product exists with the id: %s", priceId.getId())));

        return priceMapper.fromEntity(priceEntity);
    }
}