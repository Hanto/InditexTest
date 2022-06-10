package com.inditex.test.product.domain.events;// Created by jhant on 10/06/2022.

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class DomainEvent implements Serializable
{
    @EqualsAndHashCode.Include
    protected String eventId;
    protected String className;
    protected Long aggregateId;
    protected LocalDateTime occurredOn;

    // CONSTRUCTOR:
    //--------------------------------------------------------------------------------------------------------

    public DomainEvent(Long aggregateId, Class<?>eventClass)
    {
        this.eventId = UUID.randomUUID().toString();
        this.className = eventClass.getName();
        this.aggregateId = aggregateId;
        this.occurredOn = LocalDateTime.now();
    }
}