package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@KeySpace(value = "Sequences")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
class SequenceEntity implements Serializable
{
    @Id
    private String sequenceId;
    @NotNull
    private long nextValue;
    @NotNull
    private int remainingValues;

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public long getNextValue()
    {
        remainingValues--;
        long response = nextValue;
        nextValue++;
        return response;
    }
}