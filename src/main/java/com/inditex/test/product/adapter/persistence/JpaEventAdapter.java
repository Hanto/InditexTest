package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;
import com.inditex.test.product.adapter.persistence.entities.JpaDomainEventRepository;
import com.inditex.test.product.adapter.persistence.mappers.DomainEventMapper;
import com.inditex.test.product.application.port.out.EventRepository;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaEventAdapter implements EventRepository
{
    @Autowired private final JpaDomainEventRepository eventRepository;
    @Autowired private final DomainEventMapper eventMapper;

    // EVENT:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void saveNewEvent(DomainEvent event)
    {
        DomainEventEntity entity = eventMapper.fromDomain(event);
        entity.setNew(true);
        eventRepository.save(entity);
    }
}