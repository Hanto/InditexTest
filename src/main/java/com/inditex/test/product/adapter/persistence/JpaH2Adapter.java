package com.inditex.test.product.adapter.persistence;// Created by jhant on 03/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import com.inditex.test.product.adapter.persistence.entities.PriceEntity;
import com.inditex.test.product.adapter.persistence.entities.ProductEntity;
import com.inditex.test.product.adapter.persistence.mappers.PriceMapper;
import com.inditex.test.product.adapter.persistence.mappers.ProductMapper;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs.named;
import static com.inditex.test.product.adapter.persistence.entities.ProductEntity.GRAPH_PRODUCT_ALL;
import static java.lang.String.format;

@Component
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@RequiredArgsConstructor
public class JpaH2Adapter implements PersistenceRepository
{
    @Autowired private final PriceRepository priceRepo;
    @Autowired private final ProductRepository productRepo;
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