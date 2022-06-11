package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.messaging.MessagePublisher;
import com.inditex.test.product.adapter.persistence.entities.DomainEventEntity;
import com.inditex.test.product.adapter.persistence.entities.EventRelayConfigEntity;
import com.inditex.test.product.adapter.persistence.entities.JpaDomainEventRepository;
import com.inditex.test.product.adapter.persistence.entities.JpaEventRelayConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
@Log4j2
public class EventStoreConsumer
{
    @Autowired private final JpaEventRelayConfigRepository relayConfigRepo;
    @Autowired private final JpaDomainEventRepository eventRepo;
    @Autowired private final MessagePublisher messagePublisher;


    // OUTBOX RELAY CONSUMER:
    //----------------------------------------------------------------------------------------

    @Transactional
    @Scheduled(fixedDelay = 10000)
    @SchedulerLock(name = "Outbox-Relay")
    public void consume()
    {
        List<DomainEventEntity> events = eventRepo.findBySent(false, Pageable.ofSize(100)).getContent();

        for (DomainEventEntity event: events)
        {
            try
            {
                sendMessage(event);
                event.setSent(true);
            }
            catch (Exception e)
            {
                log.error(e);
                event.setSent(false);
            }
        }

        eventRepo.saveAll(events);
    }

    // EVENT PUBLISHER:
    //----------------------------------------------------------------------------------------

    private void sendMessage(DomainEventEntity event)
    {
        Optional<EventRelayConfigEntity> optional = relayConfigRepo.findById(event.getEventType());

        EventRelayConfigEntity config = optional.orElseThrow(
            () -> new EntityNotFoundException(format("No configuration found for event type: %s", event.getEventType())));

        messagePublisher.sendMessage(event.getEventJson(), config.getTopicName());
    }
}