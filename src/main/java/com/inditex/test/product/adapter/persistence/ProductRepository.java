package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{
    @EntityGraph(ProductEntity.GRAPH_ALL)
    Optional<ProductEntity>findById(Long id);
}