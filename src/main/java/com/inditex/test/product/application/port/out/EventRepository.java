package com.inditex.test.product.application.port.out;// Created by jhant on 10/06/2022.

import com.inditex.test.product.domain.events.DomainEvent;

import java.util.List;

public interface EventRepository
{
    void saveNewEvent(DomainEvent event);
    void updateAsSent(DomainEvent event);
    void updateAsUnsent(DomainEvent event);

    List<DomainEvent> retrieveUnsentMessages(int numberOfMessages);
}
