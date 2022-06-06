package com.inditex.test.configuration;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.MemoryDAO;
import com.inditex.test.product.application.PersistenceDAO;
import com.inditex.test.product.application.ProductService;
import com.inditex.test.product.application.ProductServiceI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public ProductServiceI getProductService(PersistenceDAO persistenceDAO, MemoryDAO memoryDAO)
    {   return new ProductService(persistenceDAO, memoryDAO); }
}
