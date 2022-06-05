package com.inditex.test.product.domain;// Created by jhant on 05/06/2022.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProductNameTest
{
    @Nested @DisplayName("WHEN: Invalid names")
    class Invalid
    {
        @Nested @DisplayName("WHEN: Shortname non alphanumeric")
        class ShortNameNonAlphanumeric
        {
            @Test @DisplayName("THEN: an exception is thrown")
            void test()
            {
                String shortName = ".abc%2";
                String longName = "LongName";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new ProductName(shortName, longName))
                    .withMessageContaining("Shortname: %s, should have only alphanumerical characters", shortName);
            }
        }

        @Nested @DisplayName("WHEN: Shortname with less then 3 characters or more than 50")
        class ShortNameInvalidSize
        {
            @Test @DisplayName("THEN: an exception is thrown")
            void test()
            {
                String shortName1 = "2";
                String longName = "LongName";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new ProductName(shortName1, "LongName"))
                    .withMessageContaining("Shortname: %s, should have between", shortName1);

                String shortName2 = "123456789012345678901234567";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new ProductName(shortName2, longName))
                    .withMessageContaining("Shortname: %s, should have between", shortName2);
            }
        }

        @Nested @DisplayName("WHEN: Longname non alphanumeric")
        class LongNameNonAlphanumeric
        {
            @Test @DisplayName("THEN: an exception is thrown")
            void test()
            {
                String shortName = "shortName";
                String longName = ".abc%2";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new ProductName(shortName, longName))
                    .withMessageContaining("Longname: %s, should have only alphanumerical characters", longName);
            }
        }

        @Nested @DisplayName("WHEN: Longname with less then 3 characters or more than 50")
        class LongNameInvalidSize
        {
            @Test @DisplayName("THEN: an exception is thrown")
            void test()
            {
                String shortName = "shortName";
                String longName1 = "lo";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new ProductName(shortName, longName1))
                    .withMessageContaining("Longname: %s, should have between", longName1);

                String longName2 = "123456789012345678901234567890123456789012345678901";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                        new ProductName(shortName, longName2))
                    .withMessageContaining("Longname: %s, should have between", longName2);
            }
        }

        @Nested @DisplayName("WHEN: Everything is invalid")
        class AllInvalid
        {
            @Test @DisplayName("THEN: an exception is thrown with all the errors")
            void test()
            {
                String shortName = ".%";
                String longName = ".%";

                assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                    new ProductName(shortName, longName))
                    .withMessageContaining("Shortname: %s, should have only alphanumerical characters", shortName)
                    .withMessageContaining("Shortname: %s, should have between", shortName)
                    .withMessageContaining("Longname: %s, should have only alphanumerical characters", longName)
                    .withMessageContaining("Longname: %s, should have between", longName);
            }
        }
    }

    @Nested @DisplayName("WHEN: Valid names")
    class Valid
    {
        @Test @DisplayName("THEN: The names match")
        void test()
        {
            ProductName name = new ProductName("shortName", "longName");

            assertThat(name.getShortName()).isEqualTo("shortName");
            assertThat(name.getLongName()).isEqualTo("longName");
        }
    }
}
