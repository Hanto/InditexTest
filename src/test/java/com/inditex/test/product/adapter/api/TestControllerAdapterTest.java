package com.inditex.test.product.adapter.api;// Created by jhant on 05/06/2022.

import com.inditex.test.product.application.TestUseCaseI;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestControllerAdapter.class)
class TestControllerAdapterTest
{
    @Autowired private MockMvc mockMvc;
    @MockBean private TestUseCaseI productService;
    @MockBean private PriceDTOAssembler priceAssembler;
    @MockBean private ProductDTOAssembler productAssembler;

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

        @Test @DisplayName("THEN: all products api works")
        void getProductsTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/products/{page}/{pageSize}", 1, 10);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(productService)
                .should().getProducts(1, 10);
        }

        @Test @DisplayName("THEN: product api works")
        void getProductTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/product/{productId}", 38455L);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(productService)
                .should().getProduct(38455L);
        }

        @Test @DisplayName("THEN: retrieve price api works")
        void getAssignedPriceTest() throws Exception
        {
            LocalDateTime dateTime = LocalDateTime.now();

            MockHttpServletRequestBuilder get = get("/api/price/{productId}/{brandId}/{dateTime}",
                3945, 1, dateTime);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(productService)
                .should().assignedPriceFor(3945,1, dateTime);
        }

        @Test @DisplayName("THEN: modify price api works")
        void modifyPriceTest() throws Exception
        {
            MockHttpServletRequestBuilder get = patch("/api/price/{productId}/{priceId}/{price}/{currency}",
                3945, 1, 10f, "EUR");

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(productService)
                .should().modifyPrice(3945,1, 10f, "EUR");
        }

        @Test @DisplayName("THEN: price api works")
        void getPriceTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/price/{priceId}",1);

            mockMvc.perform(get)
                .andExpect(status().isOk());

            BDDMockito.then(productService)
                .should().getPrice(1);
        }
    }
}
