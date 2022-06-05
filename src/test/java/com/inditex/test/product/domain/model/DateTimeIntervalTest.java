package com.inditex.test.product.domain.model;// Created by jhant on 04/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DateTimeIntervalTest
{
    @Nested @DisplayName("WHEN: an interval is created and end comes before start")
    public class ValidDates
    {
        @Test @DisplayName("THEN: an exception is thrown")
        public void plusTest()
        {
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DateInterval(LocalDateTime.now().plusSeconds(1), LocalDateTime.now()))
                .withMessageContaining("Start date cannot be after End date");
        }
    }

    @Nested @DisplayName("WHEN: an interval is created and end comes after start")
    public class InvalidDates
    {
        @Test @DisplayName("THEN: dates are correctly stored")
        public void plusTest()
        {
            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = LocalDateTime.now().plusSeconds(1);

            DateInterval interval = new DateInterval(start, end);
            DateInterval interval2 = new DateInterval(start, end);

            assertThat(interval).isEqualTo(interval2);
            assertThat(interval.getStartDate()).isEqualTo(start);
            assertThat(interval.getEndDate()).isEqualTo(end);
        }
    }
}