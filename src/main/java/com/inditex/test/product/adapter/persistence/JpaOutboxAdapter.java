package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.entities.JpaOutboxRepository;
import com.inditex.test.product.adapter.persistence.entities.OutboxEntity;
import com.inditex.test.product.adapter.persistence.mappers.DomainEventMapper;
import com.inditex.test.product.application.port.out.OutboxRepository;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaOutboxAdapter implements OutboxRepository
{
    @Autowired private final JpaOutboxRepository eventRepository;
    @Autowired private final DomainEventMapper eventMapper;

    // EVENT:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void saveNewEvent(DomainEvent event)
    {
        OutboxEntity entity = eventMapper.fromDomain(event);
        entity.setNew(true);
        eventRepository.save(entity);
    }
}