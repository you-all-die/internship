package com.example.internship.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author Modenov D.A
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineForm {

    @Schema(description = "id продукта.")
    private Long productId;

    @Schema(description = "Количество продукта")
    private Integer productQuantity;
}
