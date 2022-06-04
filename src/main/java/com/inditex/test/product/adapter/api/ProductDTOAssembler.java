package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component @RequiredArgsConstructor
public class ProductDTOAssembler implements RepresentationModelAssembler<Product, ProductDTO>
{
    @Autowired private final ProductDTOMapper mapper;
    @Autowired private final PriceDTOAssembler priceDTOAssembler;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override
    public ProductDTO toModel(Product entity)
    {
        ProductDTO productDTO = mapper.fromModel(entity);
        CollectionModel<PriceDTO>priceDTOs = priceDTOAssembler.toCollectionModel(entity.getPrices().getPriceList());
        productDTO.setPrices(priceDTOs.getContent());

        Link selfLink = linkTo(methodOn(WebAdapter.class)
            .getProduct(productDTO.getProductId()))
            .withSelfRel();

        productDTO.add(selfLink);

        return productDTO;
    }

    @Override
    public CollectionModel<ProductDTO> toCollectionModel(Iterable<? extends Product> entities)
    {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
