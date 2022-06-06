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

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public long generateUniqueProductId()
    {   return generateUniqueId(productRepository::getNextId, "ProductId_Sequence", 100); }

    @Override public long generateUniquePriceId()
    {   return generateUniqueId(priceRepository::getNextId, "PriceId_Sequence", 100); }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private long generateUniqueId(Supplier<Long>sequenceSupplier, String sequenceName, int sequenceLength)
    {
        Optional<SequenceEntity> optional = sequenceRepository.findById(sequenceName);

        SequenceEntity sequence = optional.orElseGet(() ->
            SequenceEntity.builder()
                .sequenceId(sequenceName)
                .nextValue(sequenceSupplier.get())
                .remainingValues(sequenceLength)
                .build());

        if (sequence.getRemainingValues() <= 0)
        {
            sequence.setNextValue(sequenceSupplier.get());
            sequence.setRemainingValues(sequenceLength);
        }

        long nextValue = sequence.getNextValue();

        sequenceRepository.save(sequence);

        return nextValue;
    }
}