package com.inditex.test.product.adapter.messaging;// Created by jhant on 11/06/2022.

public interface MessagePublisher
{
    void sendMessage(String message, String topicName);
}
