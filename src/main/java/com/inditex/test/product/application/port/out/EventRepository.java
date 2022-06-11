package com.inditex.test.product.application.port.out;// Created by jhant on 10/06/2022.

import com.inditex.test.product.domain.events.DomainEvent;

public interface EventRepository
{
    void saveNewEvent(DomainEvent event);
}