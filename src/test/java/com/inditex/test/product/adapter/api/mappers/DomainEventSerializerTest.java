package com.inditex.test.product.adapter.api.mappers;// Created by jhant on 10/06/2022.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inditex.test.product.adapter.persistence.mappers.DomainEventSerializer;
import com.inditex.test.product.domain.events.PriceChanged;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainEventSerializerTest
{
    @Test @DisplayName("THEN: can serialize/deserialize")
    public void ToJsonTest()
    {
        DomainEventSerializer serializer = createDomainEventSerializer();

        PriceChanged expected = PriceChanged.builder()
            .aggregateId(1L)
            .newPrice(new BigDecimal("25.0"))
            .oldPrice(new BigDecimal("10.0"))
            .build();

        String json = serializer.toJson(expected);
        PriceChanged result = serializer.fromJson(json, PriceChanged.class.getSimpleName());

        //assertThat(result).isEqualTo(expected);
        //assertThatAreEquals(PriceChanged::getEventId, result, expected);
        //assertThatAreEquals(PriceChanged::getType, result, expected);
        //assertThatAreEquals(PriceChanged::getAggregateId, result, expected);
        //assertThatAreEquals(PriceChanged::getOccurredOn, result, expected);
        assertThatAreEquals(PriceChanged::getNewPrice, result, expected);
        assertThatAreEquals(PriceChanged::getOldPrice, result, expected);
    }

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    public DomainEventSerializer createDomainEventSerializer()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return new DomainEventSerializer(mapper);
    }

    public void assertThatAreEquals(Function<PriceChanged, ?> function, PriceChanged result, PriceChanged expected)
    {   assertThat(function.apply(result)).isEqualTo(function.apply(expected)); }
}