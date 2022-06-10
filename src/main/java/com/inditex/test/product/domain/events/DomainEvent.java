package com.inditex.test.product.domain.events;// Created by jhant on 10/06/2022.

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
public abstract class DomainEvent implements Serializable
{
    @EqualsAndHashCode.Include
    @JsonIgnore protected String eventId;
    @JsonIgnore protected String type;
    @JsonIgnore protected Long aggregateId;
    @JsonIgnore protected LocalDateTime occurredOn;

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