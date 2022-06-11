package com.inditex.test.product.application.eventhandlers;// Created by jhant on 11/06/2022.

import com.inditex.test.common.annotations.SpringComponent;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SpringComponent
@RequiredArgsConstructor
@Log4j2
public class DomainEventHandler implements DomainListener<DomainEvent>
{
    @Override public void onApplicationEvent(DomainEvent domainEvent)
    {   log.debug("Domain event: {}", domainEvent); }
}