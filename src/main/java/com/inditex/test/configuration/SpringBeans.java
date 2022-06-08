package com.inditex.test.configuration;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.port.in.PriceUseCase;
import com.inditex.test.product.application.port.in.ProductUseCase;
import com.inditex.test.product.application.port.out.MemoryRepository;
import com.inditex.test.product.application.port.out.PersistenceRepository;
import com.inditex.test.product.application.service.PriceService;
import com.inditex.test.product.application.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public PriceUseCase getPriceManipulationService(PersistenceRepository persistenceRepository)
    {   return new PriceService(persistenceRepository); }

    @Bean
    public ProductUseCase getProductInfoService(PersistenceRepository persistenceRepository, MemoryRepository memoryRepository)
    {   return new ProductService(persistenceRepository, memoryRepository); }
}