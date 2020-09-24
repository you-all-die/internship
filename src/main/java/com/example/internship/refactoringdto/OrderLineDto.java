package com.example.internship.refactoringdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Ivan Gubanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderLineDto {

    @Schema(description = "id строки заказа")
    private Long id;

    @Schema(description = "Корзина")
    @NotNull
    private CartDto cart;

    @Schema(description = "Продукт")
    @NotNull
    private ProductDto product;

    @Schema(description = "Количество продуктов")
    @Min(1)
    private Integer productQuantity;
}
