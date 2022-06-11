package com.inditex.test.product.adapter.messaging;// Created by jhant on 10/06/2022.

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaPublisher implements MessagePublisher
{

    @Override
    public void sendMessage(String message, String topicName)
    {
        log.info("Message sent to: {} with message {}", topicName, message);
    }
}