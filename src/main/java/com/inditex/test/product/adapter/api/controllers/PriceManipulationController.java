package com.inditex.test.product.adapter.api.controllers;// Created by jhant on 04/06/2022.

import com.inditex.test.product.adapter.api.dtos.PriceDTO;
import com.inditex.test.product.adapter.api.mappers.PriceDTOAssembler;
import com.inditex.test.product.application.port.in.ModifyPriceCommand;
import com.inditex.test.product.application.port.in.PriceManipulationUseCase;
import com.inditex.test.product.domain.model.Money;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.PriceId;
import com.inditex.test.product.domain.model.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor @SuppressWarnings("all")
@Log4j2
public class PriceManipulationController
{
    @Autowired private final PriceManipulationUseCase service;
    @Autowired private final PriceDTOAssembler priceAssembler;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/price/{productId}/{brandId}/{dateTime}")
    public PriceDTO getPrice(
        @NotNull @PathVariable Long productId,
        @NotNull @PathVariable Long brandId,
        @NotNull @PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateTime)
    {
        Price price = service.assignedPriceFor(productId, brandId, dateTime);
        return priceAssembler.toModel(price);
    }

    @PatchMapping("/price/{productId}/{priceId}/{amount}/{currency}")
    public void modifyPrice(
        @PathVariable Long productId,
        @PathVariable Long priceId,
        @PathVariable Float amount,
        @PathVariable String currency)
    {
        ModifyPriceCommand command = new ModifyPriceCommand(
            new ProductId(productId), new PriceId(priceId),
            new Money(amount, currency));

        service.modifyPrice(command);
    }

    @GetMapping("/price/{priceId}")
    public PriceDTO getPrice( @NotNull @PathVariable Long priceId)
    {
        Price price = service.getPrice(priceId);
        return priceAssembler.toModel(price);
    }
}