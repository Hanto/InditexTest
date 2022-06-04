package com.inditex.test.product.adapter.api.errorhandling;

import lombok.*;

/**@author Ivan Delgado Huerta*/
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class GlobalErrors
{
    private String type;
    private String reason;
}
