package com.inditex.test.product.domain.events;// Created by jhant on 10/06/2022.

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public abstract class DomainEvent implements Serializable
{
    @EqualsAndHashCode.Include
    @NonNull private String eventId;
    @NonNull private String type;
    @NonNull private Long aggregateId;
    @NonNull private LocalDateTime occurredOn;

    // CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public DomainEvent(Long aggregateId, Class<?>eventClass)
    {
        this.eventId = UUID.randomUUID().toString();
        this.type = eventClass.getSimpleName();
        this.aggregateId = aggregateId;
        this.occurredOn = LocalDateTime.now();
    }
}