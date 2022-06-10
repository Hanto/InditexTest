package com.inditex.test.configuration;// Created by jhant on 10/06/2022.

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
(
    basePackages = JpaConfiguration.ENTITY_PACKAGES,
    repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class
)
public class JpaConfiguration
{
    public static final String ENTITY_PACKAGES = "com.inditex.test.product.adapter.persistence";
}