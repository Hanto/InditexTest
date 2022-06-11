package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import com.inditex.test.product.application.port.out.EventBus;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SpringEventBusAdapter implements EventBus
{
    @Autowired private final ApplicationEventPublisher publisher;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void publish(Collection<DomainEvent> events)
    {   events.forEach(publisher::publishEvent); }
}