package com.inditex.test.product.adapter.persistence.entities;// Created by jhant on 10/06/2022.

import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface JpaOutboxRepository extends JpaRepository<OutboxEntity, String>
{
    String SKIP_LOCKED = "-2";
    @QueryHints(@QueryHint(name = AvailableSettings.JPA_LOCK_TIMEOUT, value = SKIP_LOCKED))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Page<OutboxEntity> findBySent(boolean sent, Pageable pageable);
}