package com.inditex.test.product.adapter.api.mappers;// Created by jhant on 04/06/2022.

import com.inditex.test.product.adapter.api.BrandController;
import com.inditex.test.product.adapter.api.PriceController;
import com.inditex.test.product.adapter.api.dtos.PriceDTO;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component @RequiredArgsConstructor
public class PriceDTOAssembler implements RepresentationModelAssembler<Price, PriceDTO>
{
    @Autowired private final PriceDTOMapper mapper;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public PriceDTO toModel(Price price)
    {
        PriceDTO dto = mapper.fromModel(price);

        Link selfLink = linkTo(methodOn(PriceController.class)
            .getPrice(price.getPriceId().getId()))
            .withSelfRel();

        Link brandLink = linkTo(methodOn(BrandController.class)
            .getBrand(price.getBrandId().getId()))
            .withRel("brand");

        dto.add(selfLink);
        dto.add(brandLink);

        return dto;
    }

    public PriceDTO toModel(Price price, Product product)
    {
        PriceDTO dto = toModel(price);

        Link pricechange = getPricechange(product.getProductId().getId(), price.getPriceId().getId());

        dto.add(pricechange);

        return dto;
    }

    @Override
    public CollectionModel<PriceDTO> toCollectionModel(Iterable<? extends Price> entities)
    {   return RepresentationModelAssembler.super.toCollectionModel(entities); }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private Link getPricechange(Long productId, Long priceId)
    {
        try
        {
            return linkTo(PriceController.class
                .getMethod("modifyPrice", Long.class, Long.class, Float.class, String.class),
                productId, priceId, null, null)
                .withRel("Modify price");
        }
        catch (Exception e)
        {   throw new RuntimeException(e); }
    }
}