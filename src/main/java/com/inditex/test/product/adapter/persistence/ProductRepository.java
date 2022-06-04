package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{
}
