package com.inditex.test.product.application.eventhandlers;// Created by jhant on 11/06/2022.

import com.inditex.test.product.domain.events.DomainEvent;

public interface DomainListener<T extends DomainEvent>
{
    void onApplicationEvent(T domainEvent);
}