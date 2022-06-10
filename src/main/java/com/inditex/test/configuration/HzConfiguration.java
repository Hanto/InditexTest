package com.inditex.test.configuration;// Created by jhant on 03/06/2022.

import com.hazelcast.config.Config;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.provider.hazelcast4.HazelcastLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URL;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class HzConfiguration
{
    @Autowired private final ResourcePatternResolver resourceResolver;

    // MEMORY DATA GRID:
    //--------------------------------------------------------------------------------------------------------

    @Bean
    public HazelcastInstance hazelcastInstance() throws IOException
    {
        URL configURL = resourceResolver.getResource("classpath:hazelcast.yml").getURL();
        Config config = new YamlConfigBuilder(configURL).build();

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public HazelcastLockProvider lockProvider(HazelcastInstance hazelcastInstance) {
        return new HazelcastLockProvider(hazelcastInstance);
    }
}