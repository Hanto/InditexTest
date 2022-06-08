package com.inditex.test.common;

import lombok.extern.log4j.Log4j2;

import java.util.function.Function;

// Wraper that captures exceptions throw by functional interfaces (Consumer, Function)

/**@author Ivan Delgado Huerta*/
@Log4j2
public class Try<L, R>
{
    private final L failure;
    private final R success;
    private boolean isSuccess;

    private Try(L failure, R success)
    {
        this.failure = failure;
        this.success = success;
    }

    private static <L, R> Try<L, R> failure(L value)
    {
        Try<L, R> tried = new Try<>(value, null);
        tried.isSuccess = false;
        return tried;
    }

    private static <L, R> Try<L, R> success(R value)
    {
        Try<L, R>tried = new Try<>(null, value);
        tried.isSuccess = true;
        return tried;
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------------------

    public boolean isSuccess()
    {   return isSuccess; }

    public L getFailure()
    {   return failure; }

    public R getSuccess()
    {   return success; }

    public R orElse(R other)
    {   return isSuccess ? success : other; }

    // FUNCTIONAL INTERFACES SUPPORTED
    //--------------------------------------------------------------------------------------------------------------------

    public static <T, R> Function<T, Try<Throwable, R>> function(ThrowableFunction<T, R> function)
    {
        return (T t) ->
        {
            try
            {   return Try.success(function.apply(t));  }
            catch (Exception ex)
            {   return Try.failure(ex); }
        };
    }
}
