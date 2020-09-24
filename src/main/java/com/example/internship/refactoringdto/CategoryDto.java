package com.example.internship.refactoringdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryDto {

    @Schema(description = "id категории")
    private Long id;

    @Schema(description = "Название категории")
    @Size(max = 64)
    @NotNull
    private String name;

    @Schema(description = "Родительская категория")
    private CategoryDto parent;

    @Schema(description = "Подкатегории")
    private List<CategoryDto> subcategories;
}
