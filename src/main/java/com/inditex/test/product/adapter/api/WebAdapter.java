package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.ProductServiceI;
import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor
public class WebAdapter
{
    @Autowired private final ProductServiceI productService;
    @Autowired private final PriceDTOAssembler priceAssembler;
    @Autowired private final ProductDTOAssembler productAssembler;
    @Autowired private ApplicationEventPublisher eventPublisher;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("")
    public ApiDTO getIndex()
    {
        ApiDTO api = new ApiDTO();

        Link selfLink = linkTo(methodOn(WebAdapter.class).getIndex()).withSelfRel();
        Link allProductsLink = linkTo(methodOn(WebAdapter.class).getProducts()).withRel("All products");

        api.add(selfLink);
        api.add(allProductsLink);
        return api;
    }

    @GetMapping("/product")
    public CollectionModel<ProductDTO> getProducts()
    {
        Collection<Product> products = productService.getProducts();
        return productAssembler.toCollectionModel(products);
    }

    @GetMapping("/product/{productId}")
    public ProductDTO getProduct(@NotNull @PathVariable Long productId)
    {
        Product product = productService.getProduct(productId);
        return productAssembler.toModel(product);
    }

    @GetMapping("/price/{productId}/{brandId}/{priceListId}")
    public PriceDTO getPrice(
        @NotNull @PathVariable Long productId,
        @NotNull @PathVariable Long brandId,
        @NotNull @PathVariable Long priceListId,
        @NotNull @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)LocalDateTime time)
    {
        Price price = productService.assignedPriceFor(productId, brandId, priceListId, time);
        return priceAssembler.toModel(price);
    }

    @GetMapping("/price/{priceId}")
    public PriceDTO getPrice( @NotNull @PathVariable Long priceId)
    {
        Price price = productService.getPrice(priceId);
        return priceAssembler.toModel(price);
    }

    //TODO:
    @GetMapping("/brand/{brandId}")
    public PriceDTO getBrand( @NotNull @PathVariable Long brandId)
    {
        return null;
    }

    //TODO:
    @GetMapping("/priceList/{priceListId}")
    public PriceDTO getPriceList( @NotNull @PathVariable Long priceListId)
    {
        return null;
    }
}