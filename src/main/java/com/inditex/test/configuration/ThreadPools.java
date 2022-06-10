package com.inditex.test.configuration;// Created by jhant on 10/06/2022.

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration @EnableAsync @EnableScheduling
@Log4j2 @RequiredArgsConstructor
public class ThreadPools
{
    public static final String EVENT_PUBLISHER_POOL = "DestThreadPool";

    @Bean(name = EVENT_PUBLISHER_POOL)
    public Executor getExecutor01()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(2);
        executor.setThreadNamePrefix("EventPublisher");
        executor.initialize();
        return executor;
    }
}
