package com.inditex.test.product.adapter.api.controllers;// Created by jhant on 07/06/2022.

import com.inditex.test.product.adapter.api.dtos.ProductDTO;
import com.inditex.test.product.adapter.api.mappers.ProductDTOAssembler;
import com.inditex.test.product.application.port.in.PaginationCommand;
import com.inditex.test.product.application.port.in.ProductInfoUseCase;
import com.inditex.test.product.domain.model.Product;
import com.inditex.test.product.domain.model.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor @SuppressWarnings("all")
@Log4j2
public class ProductInfoController
{
    @Autowired private final ProductInfoUseCase service;
    @Autowired private final ProductDTOAssembler productAssembler;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/products/{page}/{pageSize}")
    public CollectionModel<ProductDTO> getProducts(
        @NotNull @PathVariable Integer page,
        @NotNull @PathVariable Integer pageSize)
    {
        PaginationCommand command = new PaginationCommand(page, pageSize);

        Collection<Product> products = service.getProducts(command);
        return productAssembler.toCollectionModel(products);
    }

    @GetMapping("/product/{productId}")
    public ProductDTO getProduct(@NotNull @PathVariable Long productId)
    {
        Product product = service.getProduct(new ProductId(productId));
        return productAssembler.toModel(product);
    }
}
