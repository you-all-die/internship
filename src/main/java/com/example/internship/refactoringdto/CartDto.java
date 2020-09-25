package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Ivan Gubanov
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartDto {

    @Schema(description = "id корзины")
    @JsonView(View.All.class)
    private Long id;

    @Schema(description = "Список товаров в корзине")
    @JsonView(View.Public.class)
    private List<OrderLineDto> orderLines;
}
