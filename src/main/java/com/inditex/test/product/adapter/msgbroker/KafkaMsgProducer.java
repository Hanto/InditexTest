package com.inditex.test.product.adapter.msgbroker;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaMsgProducer implements MsgProducer
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public void sendEvent(DomainEventEntity event, String topic)
    {
        log.info("Sending to: {} message: {}", topic, event.getEventJson());
    }
}