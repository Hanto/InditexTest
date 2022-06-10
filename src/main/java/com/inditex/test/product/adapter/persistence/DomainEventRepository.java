package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;

public interface DomainEventRepository extends EntityGraphJpaRepository<DomainEventEntity, String>
{
}