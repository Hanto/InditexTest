package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;

interface ProductRepository extends EntityGraphJpaRepository<ProductEntity, Long>
{
}