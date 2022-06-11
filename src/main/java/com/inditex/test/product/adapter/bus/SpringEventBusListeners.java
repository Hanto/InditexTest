package com.inditex.test.product.adapter.bus;// Created by jhant on 11/06/2022.

import com.inditex.test.common.annotations.SpringComponent;
import com.inditex.test.product.application.eventhandlers.DomainListener;
import com.inditex.test.product.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
@RequiredArgsConstructor @Log4j2
public class SpringEventBusListeners
{
    @Autowired private final ApplicationEventMulticaster eventMulticaster;
    @Autowired private final ApplicationContext applicationContext;

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    @PostConstruct
    public void createListeners()
    {   findAndLoadDomainListeners(); }

    // CREATE EVENT HANDLERS:
    //--------------------------------------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
    private void findAndLoadDomainListeners()
    {
        List<DomainListener> list = applicationContext.getBeansOfType(DomainListener.class).values().stream().toList();

        for (DomainListener<?> listener: list)
            eventMulticaster.addApplicationListener(createApplicationListener(listener));
    }

    private <T extends DomainEvent> ApplicationListener<PayloadApplicationEvent<T>>createApplicationListener(DomainListener<T> listener)
    {   return event -> listener.onApplicationEvent(event.getPayload()); }
}
