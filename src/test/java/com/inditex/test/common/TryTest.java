package com.inditex.test.common;// Created by jhant on 08/06/2022.

import com.inditex.test.common.functionalinterfaces.ThrowableConsumer;
import com.inditex.test.common.functionalinterfaces.ThrowableFunction;
import com.inditex.test.common.functionalinterfaces.ThrowableRunnable;
import com.inditex.test.common.functionalinterfaces.ThrowableSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TryTest extends BDDMockito
{
    // FUNCTION:
    //--------------------------------------------------------------------------------------------------------

    @Mock
    ThrowableFunction<Integer, Integer> function;

    @Nested @DisplayName("WHEN: Try.Function")
    class TryFunction
    {
        @Nested @DisplayName("WHEN: It throws an exception")
        class WhenException
        {
            @Test @DisplayName("THEN: the exception is stored as failure")
            public void tryFunctionFailure() throws Exception
            {
                when(function.apply(any()))
                    .thenThrow(new RuntimeException("Error"));

                Try<Throwable, Integer> result = Try.function(function).apply(2);

                then(function)
                    .should().apply(2);

                assertThat(result.isSuccess()).isFalse();
                assertThat(result.getSuccess()).isEqualTo(null);
                assertThat(result.getFailure()).hasMessageContaining("Error");
                assertThat(result.orElse(10)).isEqualTo(10);
            }
        }

        @Nested @DisplayName("WHEN: It doesn't throw an exception")
        class WhenNoException
        {
            @Test @DisplayName("THEN: the result is stored as success")
            public void tryFunctionSuccess() throws Exception
            {
                when(function.apply(any()))
                    .thenReturn(2);

                Try<Throwable, Integer> result = Try.function(function).apply(2);

                then(function)
                    .should().apply(2);

                assertThat(result.isSuccess()).isTrue();
                assertThat(result.getSuccess()).isEqualTo(2);
                assertThat(result.getFailure()).isNull();
                assertThat(result.orElse(10)).isEqualTo(2);
            }
        }
    }

    // SUPPLIER:
    //--------------------------------------------------------------------------------------------------------

    @Mock
    ThrowableSupplier<Integer> supplier;

    @Nested @DisplayName("WHEN: Try.Supplier")
    class TrySupplier
    {
        @Nested @DisplayName("WHEN: It throws an exception")
        class WhenException
        {
            @Test @DisplayName("THEN: the exception is stored as failure")
            public void tryFunctionFailure() throws Exception
            {
                when(supplier.get())
                    .thenThrow(new RuntimeException("Error"));

                Try<Throwable, Integer> result = Try.supplier(supplier).get();

                then(supplier)
                    .should().get();

                assertThat(result.isSuccess()).isFalse();
                assertThat(result.getSuccess()).isEqualTo(null);
                assertThat(result.getFailure()).hasMessageContaining("Error");
                assertThat(result.orElse(10)).isEqualTo(10);
            }
        }

        @Nested @DisplayName("WHEN: It doesn't throw an exception")
        class WhenNoException
        {
            @Test @DisplayName("THEN: the result is stored as success")
            public void tryFunctionSuccess() throws Exception
            {
                when(supplier.get())
                    .thenReturn(2);

                Try<Throwable, Integer> result = Try.supplier(supplier).get();

                then(supplier)
                    .should().get();

                assertThat(result.isSuccess()).isTrue();
                assertThat(result.getSuccess()).isEqualTo(2);
                assertThat(result.getFailure()).isNull();
                assertThat(result.orElse(10)).isEqualTo(2);
            }
        }
    }

    // CONSUMER:
    //--------------------------------------------------------------------------------------------------------

    @Mock
    ThrowableConsumer<Integer> consumer;

    @Nested @DisplayName("WHEN: Try.Consumer")
    class TryConsumer
    {
        @Nested @DisplayName("WHEN: It throws an exception")
        class WhenException
        {
            @Test @DisplayName("THEN: the exception is captured")
            public void tryFunctionFailure() throws Exception
            {
                willThrow(new RuntimeException())
                    .given(consumer).accept(any());

                Try.consumer(consumer).accept(2);

                then(consumer)
                    .should().accept(2);
            }
        }

        @Nested @DisplayName("WHEN: It doesn't throw an exception")
        class WhenNoException
        {
            @Test @DisplayName("THEN: the result is stored as success")
            public void tryFunctionSuccess() throws Exception
            {
                willDoNothing()
                    .given(consumer).accept(any());

                Try.consumer(consumer).accept(2);

                then(consumer)
                    .should().accept(2);
            }
        }
    }

    // RUNNABLE:
    //--------------------------------------------------------------------------------------------------------

    @Mock
    ThrowableRunnable runnable;

    @Nested @DisplayName("WHEN: Try.Runnable")
    class TryRunnable
    {
        @Nested @DisplayName("WHEN: It throws an exception")
        class WhenException
        {
            @Test @DisplayName("THEN: the exception is captured")
            public void tryFunctionFailure() throws Exception
            {
                willThrow(new RuntimeException())
                    .given(runnable).run();

                Try.runnable(runnable).run();

                then(runnable)
                    .should().run();
            }
        }

        @Nested @DisplayName("WHEN: It doesn't throw an exception")
        class WhenNoException
        {
            @Test @DisplayName("THEN: the result is stored as success")
            public void tryFunctionSuccess() throws Exception
            {
                willDoNothing()
                    .given(runnable).run();

                Try.runnable(runnable).run();

                then(runnable)
                    .should().run();
            }
        }
    }
}
