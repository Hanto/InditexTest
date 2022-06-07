package com.inditex.test.product.adapter.persistence.repositories;// Created by jhant on 04/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.inditex.test.product.adapter.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends EntityGraphJpaRepository<ProductEntity, Long>
{
    @Query(nativeQuery = true, value = "values next value for PRODUCT_ID_SEQUENCE")
    long getNextId();
}