package com.inditex.test.product.adapter.persistence;// Created by jhant on 10/06/2022.

import com.inditex.test.product.adapter.messaging.MessagePublisher;
import com.inditex.test.product.adapter.persistence.entities.JpaOutboxRepository;
import com.inditex.test.product.adapter.persistence.entities.JpaRelayConfigRepository;
import com.inditex.test.product.adapter.persistence.entities.OutboxEntity;
import com.inditex.test.product.adapter.persistence.entities.RelayConfigEntity;
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
public class JpaOutboxConsumer
{
    @Autowired private final JpaRelayConfigRepository relayConfigRepo;
    @Autowired private final JpaOutboxRepository eventRepo;
    @Autowired private final MessagePublisher messagePublisher;


    // OUTBOX RELAY CONSUMER:
    //----------------------------------------------------------------------------------------

    @Transactional
    @Scheduled(fixedDelay = 10000)
    @SchedulerLock(name = "Outbox-Relay")
    public void consume()
    {
        List<OutboxEntity> events = eventRepo.findBySent(false, Pageable.ofSize(100)).getContent();

        for (OutboxEntity event: events)
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

    private void sendMessage(OutboxEntity event)
    {
        Optional<RelayConfigEntity> optional = relayConfigRepo.findById(event.getEventType());

        RelayConfigEntity config = optional.orElseThrow(
            () -> new EntityNotFoundException(format("No configuration found for event type: %s", event.getEventType())));

        messagePublisher.sendMessage(event.getEventJson(), config.getTopicName());
    }
}