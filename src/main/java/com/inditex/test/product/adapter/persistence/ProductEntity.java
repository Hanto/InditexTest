package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.inditex.test.product.adapter.persistence.PriceEntity.PRICE_CACHE_REGION;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity @DynamicInsert @DynamicUpdate @Table(name = "PRODUCTS")
@Cache(region = PRICE_CACHE_REGION, usage = READ_WRITE)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductEntity
{
    @Id @GeneratedValue
    private long productId;

    private String shortName;

    private String longName;
}
