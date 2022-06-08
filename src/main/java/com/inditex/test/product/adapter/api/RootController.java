package com.inditex.test.product.adapter.api;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.api.dtos.ApiDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor @SuppressWarnings("all")
@Log4j2
public class RootController
{
    // ROOT: (discovery)
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("")
    public ApiDTO getIndex() throws NoSuchMethodException
    {
        ApiDTO api = new ApiDTO();

        api.add(linkTo(methodOn(RootController.class).getIndex()).withSelfRel());
        api.add(linkTo(methodOn(ProductController.class).getProducts(null, null)).withRel("All products"));
        api.add(linkTo(methodOn(ProductController.class).getProduct(null)).withRel("Product"));
        api.add(linkTo(methodOn(PriceController.class).getPrice(null, null,null)).withRel("Price for product"));
        api.add(linkTo(methodOn(PriceController.class).getPrice(null)).withRel("Price"));
        api.add(linkTo(PriceController.class.getMethod("modifyPrice", Long.class, Long.class, Float.class, String.class)).withRel("Modify price"));

        return api;
    }
}