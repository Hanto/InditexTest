package com.inditex.test.product.adapter.persistence.entities;// Created by jhant on 10/06/2022.

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity @DynamicInsert @DynamicUpdate @Table(name = "EVENT_STORE")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class DomainEventEntity implements Persistable<String>
{
    // ENTITY:
    //--------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "EVENT_ID", nullable = false)
    @Setter(AccessLevel.NONE)
    private String eventId;

    @Column(name = "EVENT_TYPE", nullable = false)
    @NotNull
    private String eventType;

    @Column(name = "AGGREGATE_ID", nullable = false)
    @NotNull
    private Long aggregateId;

    @Column(name = "OCCURED_ON", nullable = false)
    @NotNull
    private LocalDateTime occurredOn;

    @Column(name = "EVENT_JSON", nullable = false) @Lob
    @NotNull
    private String eventJson;

    @Column(name = "SENT", nullable = false)
    @NotNull
    private Boolean sent;

    // PERSISTABLE (for fast inserts:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public String getId()
    {   return eventId; }

    @Transient
    private boolean isNew = false;
}