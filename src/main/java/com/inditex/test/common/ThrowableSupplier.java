package com.inditex.test.common;

@FunctionalInterface
public interface ThrowableSupplier<T>
{
    T get() throws Exception;
}