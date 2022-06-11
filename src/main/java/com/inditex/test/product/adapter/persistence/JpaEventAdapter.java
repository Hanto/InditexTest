package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;
import com.inditex.test.product.adapter.persistence.entities.JpaDomainEventRepository;
import com.inditex.test.product.adapter.persistence.mappers.DomainEventMapper;
import com.inditex.test.product.application.port.out.EventRepository;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaEventAdapter implements EventRepository
{
    @Autowired private final JpaDomainEventRepository eventRepository;
    @Autowired private final DomainEventMapper eventMapper;

    // EVENT:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void updateAsSent(DomainEvent event)
    {
        DomainEventEntity entity = eventMapper.fromDomain(event);
        entity.setSent(true);
        eventRepository.save(entity);
    }

    @Override
    public void updateAsUnsent(DomainEvent event)
    {
        DomainEventEntity entity = eventMapper.fromDomain(event);
        entity.setSent(false);
        eventRepository.save(entity);
    }

    @Override
    public void saveNewEvent(DomainEvent event)
    {
        DomainEventEntity entity = eventMapper.fromDomain(event);
        entity.setNew(true);
        eventRepository.save(entity);
    }

    @Override
    public List<DomainEvent> retrieveUnsentMessages(int numberOfMessages)
    {
        List<DomainEventEntity> events = eventRepository.findBySent(false, Pageable.ofSize(numberOfMessages)).getContent();
        return events.stream().map(eventMapper::fromEntity).toList();
    }
}