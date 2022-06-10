package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventSerializer
{
    @Autowired private final ObjectMapper objectMapper;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public String toJson(DomainEvent event) throws JsonProcessingException
    {   return objectMapper.writeValueAsString(event); }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(String json) throws JsonProcessingException, ClassNotFoundException
    {
        ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
        Class<?>clazz = Class.forName(node.get("className").asText());
        return (T) objectMapper.readValue(json, clazz);
    }
}