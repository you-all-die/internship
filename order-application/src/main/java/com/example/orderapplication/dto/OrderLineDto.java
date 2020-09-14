package com.example.orderapplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Modenov D.A
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDto {

    @Schema(description = "id товара")
    @NotNull
    private Long productId;

    @Schema(description = "Название товара")
    @NotNull
    private String productName;

    @Schema(description = "Количество товара")
    @NotNull
    private Integer productQuantity;

    @Schema(description = "Цена товара")
    @NotNull
    private BigDecimal productPrice;
}
