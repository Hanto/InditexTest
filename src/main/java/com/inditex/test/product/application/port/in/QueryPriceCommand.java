package com.inditex.test.product.application.port.in;// Created by jhant on 07/06/2022.

import com.inditex.test.common.SelfValidating;
import com.inditex.test.product.domain.model.BrandId;
import com.inditex.test.product.domain.model.ProductId;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value @EqualsAndHashCode(callSuper = false)
public class QueryPriceCommand extends SelfValidating<ModifyPriceCommand>
{
    @NotNull ProductId productId;
    @NotNull BrandId brandId;
    @NotNull LocalDateTime localDateTime;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public QueryPriceCommand(ProductId productId, BrandId brandId, LocalDateTime localDateTime)
    {
        this.productId = productId;
        this.brandId = brandId;
        this.localDateTime = localDateTime;

        this.validateSelf();
    }
}