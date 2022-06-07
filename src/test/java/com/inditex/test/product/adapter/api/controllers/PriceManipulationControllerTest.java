package com.inditex.test.product.adapter.api.controllers;// Created by jhant on 05/06/2022.

import com.inditex.test.product.adapter.api.mappers.PriceDTOAssembler;
import com.inditex.test.product.application.port.in.ModifyPriceCommand;
import com.inditex.test.product.application.port.in.PriceManipulationUseCase;
import com.inditex.test.product.domain.model.Money;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceManipulationController.class)
public class PriceManipulationControllerTest
{
    @Autowired private MockMvc mockMvc;
    @MockBean private PriceManipulationUseCase service;
    @MockBean private PriceDTOAssembler priceAssembler;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Accessing the API Endpoints")
    class Endpoints
    {
        @Test @DisplayName("THEN: retrieve price api works")
        void getAssignedPriceTest() throws Exception
        {
            LocalDateTime dateTime = LocalDateTime.now();

            MockHttpServletRequestBuilder get = get("/api/price/{productId}/{brandId}/{dateTime}",
                3945, 1, dateTime);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(service)
                .should().assignedPriceFor(3945,1, dateTime);
        }

        @Test @DisplayName("THEN: modify price api works")
        void modifyPriceTest() throws Exception
        {
            MockHttpServletRequestBuilder get = patch("/api/price/{productId}/{priceId}/{price}/{currency}",
                3945, 1, 10f, "EUR");

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(service)
                .should().modifyPrice(eq(new ModifyPriceCommand(new ProductId(3945L), new PriceId(1L), new Money(10f, "EUR"))));
        }

        @Test @DisplayName("THEN: price api works")
        void getPriceTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/price/{priceId}",1);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(service)
                .should().getPrice(1);
        }
    }
}