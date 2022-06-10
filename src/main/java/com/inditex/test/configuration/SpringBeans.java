package com.inditex.test.configuration;// Created by jhant on 10/06/2022.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public JavaTimeModule getJavaTimeModule()
    {   return new JavaTimeModule(); }

    @Bean
    public ObjectMapper getObjectMapper(JavaTimeModule javaTimeModule)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}