package com.inditex.test.product.adapter.api;// Created by jhant on 04/06/2022.

import lombok.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Collection;
import java.util.List;

@Data @EqualsAndHashCode(callSuper = false) @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDTO extends RepresentationModel<ProductDTO>
{
    private long productId;
    private String shortName;
    private String longName;
    private Collection<PriceDTO> prices;

}
