package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import com.inditex.test.product.application.port.out.EventBus;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@RequiredArgsConstructor
public class EventBusAdapter implements EventBus
{
    @Autowired private final DomainEventRepository repository;
    @Autowired private final DomainEventMapper eventMapper;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void publish(Collection<DomainEvent> events)
    {
        Collection<DomainEventEntity>entities = events.stream()
            .map(eventMapper::fromDomain).toList();

        repository.saveAll(entities);
    }
}