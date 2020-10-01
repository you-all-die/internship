package com.example.internship.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * @author Роман Каравашкин.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductRatingDto {

    @Schema(description = "id оценки")
    private Long id;

    @Schema(description = "id продукта")
    private Long productId;
    @Schema(description = "id клиента")
    private Long customerId;
    @Schema(description = "оценка товара")
    private Long rating;
}
