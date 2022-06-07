package com.inditex.test.configuration;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.TestUseCase;
import com.inditex.test.product.application.TestUseCaseI;
import com.inditex.test.product.domain.services.MemoryRepository;
import com.inditex.test.product.domain.services.PersistenceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public TestUseCaseI getProductService(PersistenceRepository persistenceRepository, MemoryRepository memoryRepository)
    {   return new TestUseCase(persistenceRepository, memoryRepository); }
}
