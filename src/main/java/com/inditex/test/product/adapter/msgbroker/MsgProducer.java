package com.inditex.test.product.adapter.msgbroker;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;

public interface MsgProducer
{
    void sendEvent(DomainEventEntity event, String topic);
}
