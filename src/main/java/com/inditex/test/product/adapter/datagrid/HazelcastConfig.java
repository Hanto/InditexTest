package com.inditex.test.product.adapter.datagrid;// Created by jhant on 03/06/2022.

import com.hazelcast.config.Config;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URL;

@Configuration
@Log4j2 @RequiredArgsConstructor
public class HazelcastConfig
{
    @Autowired private final ResourcePatternResolver resourceResolver;

    // MEMORY DATA GRID:
    //--------------------------------------------------------------------------------------------------------

    @Bean
    public HazelcastInstance hazelcastInstance() throws IOException
    {
        URL configURL = resourceResolver.getResource("classpath:hazelcast.yml").getURL();
        Config config = new YamlConfigBuilder(configURL).build();

        return com.hazelcast.core.Hazelcast.newHazelcastInstance(config);
    }
}