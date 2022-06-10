package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inditex.test.product.domain.events.PriceChangedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainEventSerializerTest
{
    @Test @DisplayName("THEN: can serialize/deserialize")
    public void ToJsonTest() throws JsonProcessingException, ClassNotFoundException
    {
        DomainEventSerializer serializer = createDomainEventSerializer();

        PriceChangedEvent expected = PriceChangedEvent.builder()
            .aggregateId(1L)
            .newPrice(10)
            .oldPrice(5)
            .build();

        String json = serializer.toJson(expected);
        PriceChangedEvent result = serializer.fromJson(json);

        assertThat(result).isEqualTo(expected);
        assertThatAreEquals(PriceChangedEvent::getEventId, result, expected);
        assertThatAreEquals(PriceChangedEvent::getType, result, expected);
        assertThatAreEquals(PriceChangedEvent::getAggregateId, result, expected);
        assertThatAreEquals(PriceChangedEvent::getOccurredOn, result, expected);
        assertThatAreEquals(PriceChangedEvent::getNewPrice, result, expected);
        assertThatAreEquals(PriceChangedEvent::getOldPrice, result, expected);
    }

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    public DomainEventSerializer createDomainEventSerializer()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return new DomainEventSerializer(mapper);
    }

    public void assertThatAreEquals(Function<PriceChangedEvent, ?> function, PriceChangedEvent result, PriceChangedEvent expected)
    {   assertThat(function.apply(result)).isEqualTo(function.apply(expected)); }
}