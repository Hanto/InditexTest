package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.TestUseCaseI;
import com.inditex.test.product.domain.model.Price;
import com.inditex.test.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor @SuppressWarnings("all")
@Log4j2
class TestControllerAdapter
{
    @Autowired private final TestUseCaseI productService;
    @Autowired private final PriceDTOAssembler priceAssembler;
    @Autowired private final ProductDTOAssembler productAssembler;

    // ROOT: (discovery)
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("")
    public ApiDTO getIndex() throws NoSuchMethodException
    {
        ApiDTO api = new ApiDTO();

        api.add(linkTo(methodOn(TestControllerAdapter.class).getIndex()).withSelfRel());
        api.add(linkTo(methodOn(TestControllerAdapter.class).getProducts(null, null)).withRel("All products"));
        api.add(linkTo(methodOn(TestControllerAdapter.class).getProduct(null)).withRel("Product"));
        api.add(linkTo(methodOn(TestControllerAdapter.class).getPrice(null)).withRel("Price"));
        api.add(linkTo(methodOn(TestControllerAdapter.class).getPrice(null, null,null)).withRel("Price for product"));
        api.add(linkTo(TestControllerAdapter.class.getMethod("modifyPrice", Long.class, Long.class, Float.class, String.class)).withRel("Modify price"));

        return api;
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/products/{page}/{pageSize}")
    public CollectionModel<ProductDTO> getProducts(
        @NotNull @PathVariable Integer page,
        @NotNull @PathVariable Integer pageSize)
    {
        Collection<Product> products = productService.getProducts(page , pageSize);
        return productAssembler.toCollectionModel(products);
    }

    @GetMapping("/product/{productId}")
    public ProductDTO getProduct(@NotNull @PathVariable Long productId)
    {
        Product product = productService.getProduct(productId);
        return productAssembler.toModel(product);
    }

    @PatchMapping("/price/{productId}/{priceId}/{amount}/{currency}")
    public void modifyPrice(
        @PathVariable Long productId,
        @PathVariable Long priceId,
        @PathVariable Float amount,
        @PathVariable String currency)
    {
        productService.modifyPrice(productId, priceId, amount, currency);
    }

    @GetMapping("/price/{productId}/{brandId}/{dateTime}")
    public PriceDTO getPrice(
        @NotNull @PathVariable Long productId,
        @NotNull @PathVariable Long brandId,
        @NotNull @PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateTime)
    {
        Price price = productService.assignedPriceFor(productId, brandId, dateTime);
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
    public ApiDTO getBrand( @NotNull @PathVariable Long brandId)
    {
        return new ApiDTO();
    }
}