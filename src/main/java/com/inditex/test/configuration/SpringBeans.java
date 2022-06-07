package com.inditex.test.configuration;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.ProductUseCase;
import com.inditex.test.product.application.ProductUseCaseI;
import com.inditex.test.product.domain.services.MemoryRepository;
import com.inditex.test.product.domain.services.PersistenceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public ProductUseCaseI getProductService(PersistenceRepository persistenceRepository, MemoryRepository memoryRepository)
    {   return new ProductUseCase(persistenceRepository, memoryRepository); }
}
