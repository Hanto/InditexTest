package com.inditex.test.product.domain;// Created by jhant on 04/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class DateTimeIntervalTest
{
    @Nested @DisplayName("WHEN: an interval is created and end comes before start")
    public class ValidDates
    {
        @Test @DisplayName("THEN: an exception is thrown")
        public void plusTest()
        {
            try
            {
                new DateInterval(LocalDateTime.now().plusSeconds(1), LocalDateTime.now());
                failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
            }
            catch (Exception e)
            {   assertThat(e).hasMessageContaining("Start date cannot be after End date"); }
        }
    }

    @Nested @DisplayName("WHEN: an interval is created and end comes after start")
    public class InvalidDates
    {
        @Test @DisplayName("THEN: cannot sum/substract")
        public void plusTest()
        {
            assertThatNoException().isThrownBy(() ->
                new DateInterval(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1)));
        }
    }
}