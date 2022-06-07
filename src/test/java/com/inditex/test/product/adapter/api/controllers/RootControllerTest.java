package com.inditex.test.product.adapter.api.controllers;// Created by jhant on 07/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RootController.class)
public class RootControllerTest
{
    @Autowired private MockMvc mockMvc;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Accessing the API Endpoints")
    class Endpoints
    {
        @Test @DisplayName("THEN: the index api works")
        void getIndex() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/");

            mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.['self']").exists())
                .andExpect(jsonPath("$._links.['All products']").exists())
                .andExpect(jsonPath("$._links.['Product']").exists())
                .andExpect(jsonPath("$._links.['Price for product']").exists())
                .andExpect(jsonPath("$._links.['Price']").exists());
        }
    }
}