package com.inditex.test.product.adapter.bus;// Created by jhant on 10/06/2022.

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity @DynamicInsert @DynamicUpdate @Table(name = "EVENT_STORE")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class DomainEventEntity
{
    // ENTITY:
    //--------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "EVENT_ID", nullable = false)
    @Setter(AccessLevel.NONE)
    private String eventId;

    @Column(name = "TYPE", nullable = false)
    @NotNull
    private String type;

    @Column(name = "AGGREGATE_ID", nullable = false)
    @NotNull
    private Long aggregateId;

    @Column(name = "OCCURED_ON", nullable = false)
    @NotNull
    private LocalDateTime occurredOn;

    @Column(name = "EVENT_JSON", nullable = false)
    @NotNull
    private String eventJson;
}