package com.inditex.test.product.adapter.persistence;// Created by jhant on 04/06/2022.

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.inditex.test.product.domain.ProductName.*;
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
public class ProductEntity implements Persistable<Long>
{
    public static final String GRAPH_ALL = "Product.All";
    public static final String PRODUCT_CACHE_REGION = "Products";

    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID", nullable = false)
    @Setter(AccessLevel.NONE)
    private long productId;

    @Column(name = "SHORT_NAME", nullable = false)
    @NotNull @Size(min = SHORTNAME_MIN_SIZE, max = SHORTNAME_MAX_SIZE)
    private String shortName;

    @Column(name = "LONG_NAME", nullable = false)
    @NotNull @Size(min = LONGNAME_MIN_SIZE, max = LONGNAME_MAX_SIZE)
    private String longName;

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
    private List<PriceEntity>prices = new ArrayList<>();

    // PERSISTABLE (for fast inserts:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Long getId()
    {   return productId; }

    @Transient
    private boolean isNew = false;
}