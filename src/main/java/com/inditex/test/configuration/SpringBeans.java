package com.inditex.test.configuration;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.port.in.PriceManipulationUseCase;
import com.inditex.test.product.application.port.in.ProductInfoUseCase;
import com.inditex.test.product.application.port.out.MemoryRepository;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.application.service.PriceManipulationService;
import com.inditex.test.product.application.service.ProductInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public PriceManipulationUseCase getPriceManipulationService(PersistenceRepository persistenceRepository, MemoryRepository memoryRepository)
    {   return new PriceManipulationService(persistenceRepository, memoryRepository); }

    @Bean
    public ProductInfoUseCase getProductInfoService(PersistenceRepository persistenceRepository, MemoryRepository memoryRepository)
    {   return new ProductInfoService(persistenceRepository, memoryRepository); }
}