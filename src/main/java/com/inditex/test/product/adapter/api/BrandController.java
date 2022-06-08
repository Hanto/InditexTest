package com.inditex.test.product.adapter.api;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.api.dtos.ApiDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor @SuppressWarnings("all")
@Log4j2
public class BrandController
{
    //TODO:
    @GetMapping("/brand/{brandId}")
    public ApiDTO getBrand(@NotNull @PathVariable Long brandId)
    {   return new ApiDTO(); }
}
