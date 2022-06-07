package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.function.Supplier;

@Setter @AllArgsConstructor @NoArgsConstructor @Builder
class SequenceEntity implements Serializable
{
    @Id private String sequenceId;
    @NotNull private long nextValue;
    @NotNull private int remainingValues;
    @NotNull private int supplierInterval;

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public long getNextValue(Supplier<Long>sequenceSupplier)
    {
        if (hasNoRemainingValues())
            retrieveNewIntervalOfValues(sequenceSupplier);

        return returnNextValue();
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private boolean hasNoRemainingValues()
    {   return remainingValues <= 0; }

    private void retrieveNewIntervalOfValues(Supplier<Long>sequenceSupplier)
    {
        nextValue = sequenceSupplier.get();
        remainingValues = supplierInterval;
    }

    private long returnNextValue()
    {
        remainingValues--;
        long response = nextValue;
        nextValue++;
        return response;
    }
}