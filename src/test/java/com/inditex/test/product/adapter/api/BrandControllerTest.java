package com.inditex.test.product.adapter.api;// Created by jhant on 08/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BrandController.class)
public class BrandControllerTest
{
    @Autowired private MockMvc mockMvc;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Accessing the API Endpoints")
    class Endpoints
    {
        @Test @DisplayName("THEN: all products api works")
        void getProductsTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/brand/{brandId}", 1);

            mockMvc.perform(get)
                .andExpect(status().isOk());
        }
    }
}