package com.inditex.test.common;// Created by jhant on 08/06/2022.

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface UseCase
{
    @AliasFor(annotation = Service.class)
    String value() default "";
}