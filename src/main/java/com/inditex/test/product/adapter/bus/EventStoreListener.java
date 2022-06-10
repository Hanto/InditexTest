package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.JpaEventAdapter;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventStoreListener implements ApplicationListener<PayloadApplicationEvent<? extends DomainEvent>>
{
    @Autowired private final JpaEventAdapter eventAdapter;

    // PRODUCTS:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<? extends DomainEvent> event)
    {
        eventAdapter.saveEvents(event.getPayload());
    }
}