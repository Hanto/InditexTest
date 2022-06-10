package com.inditex.test.product.adapter.persistence.mappers;// Created by jhant on 10/06/2022.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class DomainEventSerializer
{
    @Autowired private final ObjectMapper objectMapper;

    private static final String EVENT_PACKAGE = DomainEvent.class.getPackageName();

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public String toJson(DomainEvent event)
    {
        try
        {   return objectMapper.writeValueAsString(event); }
        catch (Exception e)
        {   throw new IllegalArgumentException("Cannot serialize it", e); }
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(String json)
    {
        try
        {
            ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
            String className = format("%s.%s", EVENT_PACKAGE, node.get("type").asText());
            Class<?> clazz = Class.forName(className);
            return (T) objectMapper.readValue(json, clazz);
        }
        catch (Exception e)
        {   throw new IllegalArgumentException("Cannot deserialize it", e); }
    }
}