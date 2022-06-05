package com.inditex.test.product.adapter.persistence;// Created by jhant on 03/06/2022.

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity @DynamicInsert @DynamicUpdate @Table(name = "PRICES", indexes =
{
    @Index(name = "productIdIndex", columnList = "productId")
})
@Cache(region = PriceEntity.PRICE_CACHE_REGION, usage = READ_WRITE)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class PriceEntity implements Persistable<Long>
{
    public static final String PRICE_CACHE_REGION = "Price";

    // ENTITY:
    //--------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "PRICE_ID", nullable = false)
    private long priceId;

    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @NotNull
    private long productId;

    @Column(name = "BRAND_ID", nullable = false)
    @NotNull
    private long brandId;

    @Column(name = "PRICE_LIST_ID", nullable = false)
    @NotNull
    private long priceListId;

    @Column(name = "START_DATE", nullable = false)
    @NotNull
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    @NotNull
    private LocalDateTime endDate;

    @Column(name = "PRIORITY", nullable = false)
    @NotNull
    private int priority;

    @Column(name = "CURRENCY", nullable = false)
    @NotNull
    private String currency;

    @Column(name = "MONEY", nullable = false)
    @NotNull
    private BigDecimal money;

    // PERSISTABLE (for fast inserts:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Long getId()
    {   return priceId; }

    @Transient
    private boolean isNew = false;
}