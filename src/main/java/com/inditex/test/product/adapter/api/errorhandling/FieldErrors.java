package com.inditex.test.product.adapter.api.errorhandling;

import lombok.*;

/**@author Ivan Delgado Huerta */
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class FieldErrors
{
    private String entityName;
    private String fieldName;
    private String fieldValue;
    private String reason;
}
