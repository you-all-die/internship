package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@ToString
public class ProductDto {

    @Schema(description = "id продукта")
    @JsonView({View.All.class, View.Public.class})
    private Long id;

    @Schema(description = "id категории")
    @JsonView(View.Public.class)
    @NotNull
    private Long categoryId;

    @Schema(description = "Название категории")
    @JsonView(View.Public.class)
    @NotNull
    private String categoryName;

    @Schema(description = "Имя")
    @JsonView(View.Public.class)
    @Size(max = 64)
    @NotNull
    private String name;

    @Schema(description = "Описание")
    @JsonView(View.Public.class)
    @Size(max = 64)
    private String description;

    @Schema(description = "Изображение")
    @JsonView(View.Public.class)
    @Size(max = 64)
    private String picture;

    @Schema(description = "Цена")
    @JsonView(View.Public.class)
    private BigDecimal price;

    @Schema(description = "Статус продукта")
    @JsonView(View.Public.class)
    @NotNull
    private ProductStatusDto status;
}
