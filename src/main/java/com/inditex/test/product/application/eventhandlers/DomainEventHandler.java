package com.inditex.test.product.application.eventhandlers;// Created by jhant on 10/06/2022.

import com.inditex.test.common.SpringComponent;
import com.inditex.test.product.application.port.out.EventRepository;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@RequiredArgsConstructor
public class DomainEventHandler implements DomainListener<DomainEvent>
{
    @Autowired private final EventRepository eventAdapter;

    // PRODUCTS:
    //--------------------------------------------------------------------------------------------------------

    @Override public void onApplicationEvent(DomainEvent event)
    {   eventAdapter.saveNewEvent(event); }
}