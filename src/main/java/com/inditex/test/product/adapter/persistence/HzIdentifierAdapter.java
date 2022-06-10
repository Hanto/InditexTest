package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.inditex.test.product.adapter.persistence.entities.JpaPriceRepository;
import com.inditex.test.product.adapter.persistence.entities.JpaProductRepository;
import com.inditex.test.product.adapter.persistence.entities.SequenceEntity;
import com.inditex.test.product.application.port.out.IdentifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class HzIdentifierAdapter implements IdentifierRepository
{
    @Autowired private final HazelcastInstance hazelcast;
    @Autowired private final JpaPriceRepository jpaPriceRepository;
    @Autowired private final JpaProductRepository jpaProductRepository;

    private static final String SEQUENCE_MAP_REGION = "SEQUENCE_MAP_REGION";
    private static final String PRODUCT_ID_SEQUENCE = "ProductId_Sequence";
    private static final String PRICE_ID_SEQUENCE = "PriceId_Sequence";
    private static final int PRODUCT_ID_INTERVAL = 100;
    private static final int PRICE_ID_INTERVAL = 100;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public long generateUniqueProductId()
    {   return generateUniqueId(jpaProductRepository::getNextId, PRODUCT_ID_SEQUENCE, PRODUCT_ID_INTERVAL); }

    @Override public long generateUniquePriceId()
    {   return generateUniqueId(jpaPriceRepository::getNextId, PRICE_ID_SEQUENCE, PRICE_ID_INTERVAL); }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private long generateUniqueId(Supplier<Long>sequenceSupplier, String sequenceName, int sequenceInterval)
    {
        IMap<String, SequenceEntity>sequenceMap = hazelcast.getMap(SEQUENCE_MAP_REGION);
        sequenceMap.lock(sequenceName);

        SequenceEntity sequence = sequenceMap.computeIfAbsent(sequenceName, s -> SequenceEntity.builder()
            .sequenceId(sequenceName)
            .nextValue(sequenceSupplier.get())
            .remainingValues(sequenceInterval)
            .supplierInterval(sequenceInterval)
            .build());

        long nextValue = sequence.getNextValue(sequenceSupplier);

        sequenceMap.put(sequenceName, sequence);
        sequenceMap.unlock(sequenceName);

        return nextValue;
    }
}