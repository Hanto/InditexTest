package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity @DynamicInsert @DynamicUpdate @Table(name = "PRODUCTS")
@Cache(region = ProductEntity.PRODUCT_CACHE_REGION, usage = READ_WRITE)
@NamedEntityGraphs
({
    @NamedEntityGraph
    (   name = ProductEntity.GRAPH_ALL, attributeNodes =
        {
            @NamedAttributeNode(value = "prices")
        }
    )
})
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductEntity
{
    public static final String GRAPH_ALL = "Product.All";
    public static final String PRODUCT_CACHE_REGION = "Products";

    @Id @GeneratedValue
    private long productId;

    private String shortName;

    private String longName;

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
    private List<PriceEntity>prices = new ArrayList<>();
}