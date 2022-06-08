package com.inditex.test.product.adapter.api.errorhandling;// Created by jhant on 26/05/2022.

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApiError
{
    private HttpStatus status;
    private String shortMessage;
    private String localizedMessage;
    @JsonInclude(NON_EMPTY)
    private List<FieldErrors> fieldErrors;
}
