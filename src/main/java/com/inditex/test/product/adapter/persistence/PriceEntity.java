package com.inditex.test.product.adapter.persistence;// Created by jhant on 03/06/2022.

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.inditex.test.product.adapter.persistence.PriceEntity.PRICE_CACHE_REGION;
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

    private long productId;

    private long brandId;

    private long priceListId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int priority;

    private String currency;
    private BigDecimal money;


    // PERSISTABLE (for fast inserts:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public Long getId()
    {   return priceId; }

    @Transient
    private boolean isNew = false;
}
