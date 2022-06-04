package com.inditex.test.configuration;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.ProductDAO;
import com.inditex.test.product.application.ProductService;
import com.inditex.test.product.application.ProductServiceI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans
{
    @Bean
    public ProductServiceI getProductService(ProductDAO productDAO)
    {   return new ProductService(productDAO); }
}
