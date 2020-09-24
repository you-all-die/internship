package com.example.internship.refactoringdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Ivan Gubanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDto {

    @Schema(description = "id продукта")
    private Long id;

    @Schema(description = "Категория")
    @NotNull
    private CategoryDto category;

    @Schema(description = "Имя")
    @Size(max = 64)
    @NotNull
    private String name;

    @Schema(description = "Описание")
    @Size(max = 64)
    private String description;

    @Schema(description = "Изображение")
    @Size(max = 64)
    private String picture;

    @Schema(description = "Цена")
    private BigDecimal price;

    @Schema(description = "Статус продукта")
    @NotNull
    private ProductStatusDto status;
}
