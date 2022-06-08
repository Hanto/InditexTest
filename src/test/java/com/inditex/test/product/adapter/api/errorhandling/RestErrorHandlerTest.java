package com.inditex.test.product.adapter.api.errorhandling;// Created by jhant on 08/06/2022.

import com.inditex.test.product.adapter.api.ProductController;
import com.inditex.test.product.adapter.api.mappers.ProductDTOAssembler;
import com.inditex.test.product.application.port.in.ProductUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.function.Supplier;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class RestErrorHandlerTest
{
    @Autowired private MockMvc mockMvc;
    @MockBean private ProductUseCase service;
    @MockBean private ProductDTOAssembler productAssembler;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Nested @DisplayName("WHEN: there are errors accessing the API endpoints")
    public class ApiErrors
    {
        @Test @DisplayName("THEN: custom error handling when ConstraintViolation")
        public void handleConstraintViolationTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/products/{page}/{pageSize}", 0, 10);

            mockMvc.perform(get)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.shortMessage").value("Invalid Parameters"))
                .andExpect(jsonPath("$.localizedMessage").exists());
        }

        @Test @DisplayName("THEN: custom error handling when MethodArgumentTypeMismatch")
        public void handleMethodArgumentTypeMismatchTest() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/products/{page}/{pageSize}", 1, "hello");

            mockMvc.perform(get)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.shortMessage", containsString("Invalid URL: parameter pageSize")))
                .andExpect(jsonPath("$.localizedMessage").exists());
        }

        @Test @DisplayName("THEN: custom error handling when NoHandlerFoundException")
        public void handleNoHandlerFoundException() throws Exception
        {
            MockHttpServletRequestBuilder get = get("/api/products/nothing");

            mockMvc.perform(get)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.shortMessage", containsString("Invalid URL: No handler found for")))
                .andExpect(jsonPath("$.localizedMessage").exists());
        }

        @Test @DisplayName("THEN: custom error handling for internal server error")
        public void handleAll() throws Exception
        {
            whenLogicThrowsAnException(() -> new NullPointerException("Random Exception"));

            MockHttpServletRequestBuilder get = get("/api/products/{page}/{pageSize}", 1, 10);

            mockMvc.perform(get)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.shortMessage", containsString("Error Ocurred")))
                .andExpect(jsonPath("$.localizedMessage", containsString("Random Exception")));
        }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    void whenLogicThrowsAnException(Supplier<Exception>supplier)
    {
        BDDMockito.given(productAssembler.toCollectionModel(anyCollection()))
            .willThrow(supplier.get());

    }

}