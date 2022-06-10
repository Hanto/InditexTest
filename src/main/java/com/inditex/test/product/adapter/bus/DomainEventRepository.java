package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;

public interface DomainEventRepository extends EntityGraphJpaRepository<DomainEventEntity, String>
{
}