package com.inditex.test.product.adapter.persistence.entities;// Created by jhant on 10/06/2022.

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity @DynamicInsert @DynamicUpdate @Table(name = "EVENT_RELAY_CONFIG")
@Cache(region = RelayConfigEntity.RELAYCONFIG_CACHE_REGION, usage = READ_WRITE)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class RelayConfigEntity
{
    public static final String RELAYCONFIG_CACHE_REGION = "RelayConfig";

    // ENTITY:
    //--------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "EVENT_TYPE", nullable = false)
    private String eventType;

    @Column(name = "TOPIC_NAME", nullable = false)
    private String topicName;
}