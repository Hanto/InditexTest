package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data @EqualsAndHashCode(callSuper = false) @NoArgsConstructor @AllArgsConstructor @Builder
class ProductDTO extends RepresentationModel<ProductDTO>
{
    private long productId;
    private String shortName;
    private String longName;
    @JsonInclude(NON_EMPTY)
    private Collection<PriceDTO> prices;

}
