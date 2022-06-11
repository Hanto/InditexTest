package com.inditex.test.common.functionalinterfaces;

/**@author Ivan Delgado Huerta*/
@FunctionalInterface
public interface ThrowableConsumer<T>
{
    void accept(T t) throws Exception;
}
