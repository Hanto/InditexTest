package com.inditex.test.product.adapter.persistence.mappers;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component @Scope(SCOPE_SINGLETON)
@RequiredArgsConstructor
public class DomainEventMapper
{
    @Autowired private final DomainEventSerializer serializer;

    // FROM DOMAIN:
    //--------------------------------------------------------------------------------------------------------

    public DomainEventEntity fromDomain(DomainEvent event)
    {
        return DomainEventEntity.builder()
            .eventId(event.getEventId())
            .type(event.getType())
            .aggregateId(event.getAggregateId())
            .occurredOn(event.getOccurredOn())
            .eventJson(serializer.toJson(event))
            .sent(false)
            .build();
    }

    // FROM ENTITY:
    //--------------------------------------------------------------------------------------------------------

    public DomainEvent fromEntity(DomainEventEntity entity)
    {   return serializer.fromJson(entity.getEventJson(), entity.getType()); }
}