package com.inditex.test.product.domain;// Created by jhant on 03/06/2022.

import lombok.NonNull;
import lombok.Value;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isAlphanumeric;

@Value
public class ProductName
{
    @NonNull String shortName;
    @NonNull String longName;

    private static final int SHORTNAME_MIN_SIZE = 3;
    private static final int SHORTNAME_MAX_SIZE = 20;
    private static final int LONGNAME_MIN_SIZE = 3;
    private static final int LONGNAME_MAX_SIZE = 100;

    // CONSTRUCTORS:
    //--------------------------------------------------------------------------------------------------------

    public ProductName(String shortName, String longName)
    {
        this.shortName = shortName;
        this.longName = longName;
        validate();
    }

    // VALIDATOR:
    //--------------------------------------------------------------------------------------------------------

    private void validate()
    {
        if (!hasCorrectName(shortName) || hasIncorrectSize(shortName, SHORTNAME_MIN_SIZE, SHORTNAME_MAX_SIZE))
            throw new IllegalArgumentException(format("Short name must be alphanumeric and have between %s and %s characters", SHORTNAME_MIN_SIZE, SHORTNAME_MAX_SIZE));

        if (!hasCorrectName(longName) || hasIncorrectSize(longName, LONGNAME_MIN_SIZE, LONGNAME_MAX_SIZE))
            throw new IllegalArgumentException(format("Long name must be alphanumeric and have between %s and %s characters", LONGNAME_MIN_SIZE, LONGNAME_MAX_SIZE));
    }

    private boolean hasCorrectName(String name)
    {   return name.matches("[a-zA-z\\s]*"); }

    private boolean hasIncorrectSize(String string, int minSize, int maxSize)
    {   return string.length() < minSize || string.length() > maxSize; }
}
