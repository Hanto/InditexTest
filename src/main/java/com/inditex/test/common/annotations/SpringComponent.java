package com.inditex.test.common.annotations;// Created by jhant on 11/06/2022.

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SpringComponent
{
    @AliasFor(annotation = Component.class)
    String value() default "";
}