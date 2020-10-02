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

/**
 * @author Ivan Gubanov
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CategoryDto {

    @Schema(description = "id категории")
    @JsonView({View.All.class, View.Public.class})
    private Long id;

    @Schema(description = "Название категории")
    @JsonView(View.Public.class)
    @Size(max = 64)
    @NotNull
    private String name;

    @Schema(description = "id родительской категории")
    @JsonView(View.Public.class)
    private Long parentId;

    @Schema(description = "Название родительской категории")
    @JsonView(View.Public.class)
    private String parentName;
}
