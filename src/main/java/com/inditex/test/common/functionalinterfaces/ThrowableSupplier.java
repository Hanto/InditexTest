package com.inditex.test.common.functionalinterfaces;

@FunctionalInterface
public interface ThrowableSupplier<T>
{
    T get() throws Exception;
}