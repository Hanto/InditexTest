package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component @RequiredArgsConstructor
class PriceDTOAssembler implements RepresentationModelAssembler<Price, PriceDTO>
{
    @Autowired private final PriceDTOMapper mapper;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public PriceDTO toModel(Price entity)
    {
        PriceDTO dto = mapper.fromModel(entity);

        Link selfLink = linkTo(methodOn(WebAdapter.class)
            .getPrice(entity.getPriceId().getId()))
            .withSelfRel();

        Link productLink = linkTo(methodOn(WebAdapter.class)
            .getProduct(entity.getProductId().getId()))
            .withRel("product");

        Link brandLink = linkTo(methodOn(WebAdapter.class)
            .getBrand(entity.getBrandId().getId()))
            .withRel("brand");

        dto.add(selfLink);
        dto.add(productLink);
        dto.add(brandLink);

        return dto;
    }

    @Override
    public CollectionModel<PriceDTO> toCollectionModel(Iterable<? extends Price> entities)
    {   return RepresentationModelAssembler.super.toCollectionModel(entities); }
}