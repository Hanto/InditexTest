package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.inditex.test.product.application.ProductServiceI;
import com.inditex.test.product.domain.Price;
import com.inditex.test.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController @RequestMapping(value ="/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
@RequiredArgsConstructor @SuppressWarnings("all")
public class WebAdapter
{
    @Autowired private final ProductServiceI productService;
    @Autowired private final PriceDTOAssembler priceAssembler;
    @Autowired private final ProductDTOAssembler productAssembler;
    @Autowired private ApplicationEventPublisher eventPublisher;

    // ROOT: (discovery)
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("")
    public ApiDTO getIndex()
    {
        ApiDTO api = new ApiDTO();

        api.add(linkTo(methodOn(WebAdapter.class).getIndex()).withSelfRel());
        api.add(linkTo(methodOn(WebAdapter.class).getProducts(null, null)).withRel("All products"));
        api.add(linkTo(methodOn(WebAdapter.class).getProduct(null)).withRel("Product"));
        api.add(linkTo(methodOn(WebAdapter.class).getPrice(null, null, null, null)).withRel("Price for product"));
        api.add(linkTo(methodOn(WebAdapter.class).getPrice(null)).withRel("Price"));

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

    @GetMapping("/price/{productId}/{brandId}/{priceListId}/{dateTime}")
    public PriceDTO getPrice(
        @NotNull @PathVariable Long productId,
        @NotNull @PathVariable Long brandId,
        @NotNull @PathVariable Long priceListId,
        @NotNull @PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateTime)
    {
        Price price = productService.assignedPriceFor(productId, brandId, priceListId, dateTime);
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