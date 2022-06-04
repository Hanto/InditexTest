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

    @Id @GeneratedValue
    private long priceId;

    @NotNull
    private long productId;

    @NotNull
    private long brandId;

    @NotNull
    private long priceListId;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private int priority;

    @NotNull
    private String currency;

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