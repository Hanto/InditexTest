package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import com.inditex.test.product.application.MemoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
class HazelcastAdapter implements MemoryDAO
{
    @Autowired private final SequenceRepository sequenceRepository;
    @Autowired private final ProductRepository productRepository;
    @Autowired private final PriceRepository priceRepository;

    private static final String PRODUCT_ID_SEQUENCE = "ProductId_Sequence";
    private static final String PRICE_ID_SEQUENCE = "PriceId_Sequence";
    private static final int PRODUCT_ID_INTERVAL = 100;
    private static final int PRICE_ID_INTERVAL = 100;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public long generateUniqueProductId()
    {   return generateUniqueId(productRepository::getNextId, PRODUCT_ID_SEQUENCE, PRODUCT_ID_INTERVAL); }

    @Override public long generateUniquePriceId()
    {   return generateUniqueId(priceRepository::getNextId, PRICE_ID_SEQUENCE, PRICE_ID_INTERVAL); }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private long generateUniqueId(Supplier<Long>sequenceSupplier, String sequenceName, int sequenceInterval)
    {
        Optional<SequenceEntity> optional = sequenceRepository.findById(sequenceName);

        SequenceEntity sequence = optional.orElseGet(() ->
            SequenceEntity.builder()
                .sequenceId(sequenceName)
                .nextValue(sequenceSupplier.get())
                .remainingValues(sequenceInterval)
                .supplierInterval(sequenceInterval)
                .build());

        long nextValue = sequence.getNextValue(sequenceSupplier);

        sequenceRepository.save(sequence);

        return nextValue;
    }
}