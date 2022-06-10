package com.inditex.test.product.application.port.in;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.spring.SelfValidating;
import com.inditex.test.product.domain.model.Money;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.ProductId;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value @EqualsAndHashCode(callSuper = false)
public class ModifyPriceCommand extends SelfValidating<ModifyPriceCommand>
{
    @NotNull ProductId productId;
    @NotNull PriceId priceId;
    @NotNull Money money;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public ModifyPriceCommand(ProductId productId, PriceId priceId, Money money)
    {
        this.productId = productId;
        this.priceId = priceId;
        this.money = money;

        this.validateSelf();
    }
}