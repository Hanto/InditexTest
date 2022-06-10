package com.inditex.test.product.adapter.persistence.entities;// Created by jhant on 03/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

public interface JpaPriceRepository extends EntityGraphJpaRepository<PriceEntity, Long>
{
    @Query(nativeQuery = true, value = "values next value for PRICE_ID_SEQUENCE")
    long getNextId();

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    List<PriceEntity> findByProductId(Long productId);
}