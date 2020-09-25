package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Ivan Gubanov
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductStatusDto {

    @Schema(description = "id статуса продукта")
    @JsonView(View.All.class)
    private Long id;

    @Schema(description = "Описание")
    @Size(max = 64)
    @NotNull
    private String description;
}
