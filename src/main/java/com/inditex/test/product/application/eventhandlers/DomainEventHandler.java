package com.inditex.test.product.application.eventhandlers;// Created by jhant on 10/06/2022.

import com.inditex.test.product.application.port.out.EventRepository;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventHandler
{
    @Autowired private final EventRepository eventAdapter;

    // PRODUCTS:
    //--------------------------------------------------------------------------------------------------------

    @EventListener
    public void onApplicationEvent(PayloadApplicationEvent<? extends DomainEvent> event)
    {   eventAdapter.saveNewEvent(event.getPayload()); }
}