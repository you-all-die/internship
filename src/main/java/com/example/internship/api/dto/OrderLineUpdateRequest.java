package com.example.internship.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author Modenov D.A
 *
 * Refactoring Modenov.D 25.09.20
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderLineUpdateRequest {

    @Schema(description = "id продукта.")
    private Long productId;

    @Schema(description = "Количество продукта")
    private Integer productQuantity;
}
