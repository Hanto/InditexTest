package com.inditex.test.product.application.port.in;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.spring.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value @EqualsAndHashCode(callSuper = false)
public class PaginationCommand extends SelfValidating<PaginationCommand>
{
    @NotNull @Min(1)
    int page;
    @NotNull @Min(1) @Max(100)
    int pageSize;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public PaginationCommand(int page, int pageSize)
    {
        this.page = page;
        this.pageSize = pageSize;

        this.validateSelf();
    }
}