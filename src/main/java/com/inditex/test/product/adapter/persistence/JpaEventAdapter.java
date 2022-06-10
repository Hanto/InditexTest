package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.mappers.DomainEventMapper;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaEventAdapter
{
    @Autowired private final DomainEventRepository eventRepository;
    @Autowired private final DomainEventMapper eventMapper;

    // EVENT:
    //--------------------------------------------------------------------------------------------------------

    public void saveEvents(DomainEvent event)
    {   eventRepository.save(eventMapper.fromDomain(event)); }
}