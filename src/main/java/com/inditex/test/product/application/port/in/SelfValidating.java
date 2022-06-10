package com.inditex.test.product.application.port.in;// Created by jhant on 07/06/2022.

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public abstract class SelfValidating<T>
{
    @SuppressWarnings("unchecked")
    protected void validateSelf()
    {
        try
        (   ValidatorFactory factory = buildDefaultValidatorFactory())
        {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate((T) this);

            if (!violations.isEmpty())
                throw new ConstraintViolationException(violations);
        }
    }
}