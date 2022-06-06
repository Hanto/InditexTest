package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @EqualsAndHashCode(callSuper = false) @NoArgsConstructor @AllArgsConstructor @Builder
class PriceDTO extends RepresentationModel<PriceDTO>
{
    private long priceId;
    private long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int priority;
    private String currency;
    private BigDecimal price;
}