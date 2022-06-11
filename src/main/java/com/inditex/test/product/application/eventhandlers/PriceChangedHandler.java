package com.inditex.test.product.application.eventhandlers;// Created by jhant on 10/06/2022.

import com.inditex.test.common.annotations.SpringComponent;
import com.inditex.test.product.application.port.out.OutboxRepository;
import com.inditex.test.product.domain.events.PriceChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringComponent
@RequiredArgsConstructor
public class PriceChangedHandler implements DomainListener<PriceChanged>
{
    @Autowired private final OutboxRepository outboxRepository;

    // PRODUCTS:
    //--------------------------------------------------------------------------------------------------------

    @Override
    @Transactional
    public void onApplicationEvent(PriceChanged event)
    {   outboxRepository.saveNewEvent(event); }
}