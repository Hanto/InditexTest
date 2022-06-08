package com.inditex.test.product.adapter.api.mappers;// Created by jhant on 04/06/2022.

import com.inditex.test.product.adapter.api.controllers.ProductController;
import com.inditex.test.product.adapter.api.dtos.PriceDTO;
import com.inditex.test.product.adapter.api.dtos.ProductDTO;
import com.inditex.test.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
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
        List<PriceDTO> priceDTOs = entity.getPrices().getPriceList().stream()
            .map(price -> priceDTOAssembler.toModel(price, entity))
            .toList();

        productDTO.setPrices(priceDTOs);

        Link selfLink = linkTo(methodOn(ProductController.class)
            .getProduct(productDTO.getProductId()))
            .withSelfRel();

        productDTO.add(selfLink);

        return productDTO;
    }

    @Override
    public CollectionModel<ProductDTO> toCollectionModel(Iterable<? extends Product> entities)
    {   return RepresentationModelAssembler.super.toCollectionModel(entities); }
}