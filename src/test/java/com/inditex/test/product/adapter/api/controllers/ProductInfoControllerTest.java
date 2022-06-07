package com.inditex.test.product.adapter.api.controllers;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.api.mappers.ProductDTOAssembler;
import com.inditex.test.product.application.port.in.ProductInfoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductInfoController.class)
public class ProductInfoControllerTest
{
    @Autowired private MockMvc mockMvc;
    @MockBean private ProductInfoUseCase service;
    @MockBean private ProductDTOAssembler productAssembler;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: Accessing the API Endpoints")
    class Endpoints
    {
        @Test
        @DisplayName("THEN: all products api works")
        void getProductsTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/products/{page}/{pageSize}", 1, 10);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(service)
                .should().getProducts(1, 10);
        }

        @Test @DisplayName("THEN: product api works")
        void getProductTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/product/{productId}", 38455L);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(service)
                .should().getProduct(38455L);
        }
    }
}