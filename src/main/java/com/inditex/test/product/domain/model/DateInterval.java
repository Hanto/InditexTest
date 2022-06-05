package com.inditex.test.product.domain.model;// Created by jhant on 04/06/2022.

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class DateInterval
{
    @NonNull LocalDateTime startDate;
    @NonNull LocalDateTime endDate;

    // CONSTRUCTORS:
    //--------------------------------------------------------------------------------------------------------

    public DateInterval(LocalDateTime startDate, LocalDateTime endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        validate();
    }

    // BUSINESS:
    //--------------------------------------------------------------------------------------------------------

    public boolean isInDateInTheInterval(LocalDateTime date)
    {   return startDate.isBefore(date) && endDate.isAfter(date); }

    // VALIDATION:
    //--------------------------------------------------------------------------------------------------------

    private void validate()
    {
        if (startDate.isAfter(endDate))
            throw new IllegalArgumentException("Start date cannot be after End date");
    }
}
