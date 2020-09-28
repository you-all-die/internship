package com.example.internship.api.dto;

import com.example.internship.refactoringdto.CustomerDto;
import com.example.internship.refactoringdto.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Romodin Aleksey
 *
 * Refactoring Modenov.D 25.09.20
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerSearchResponse {

    @Schema(description = "Пользователи")
    @JsonView(View.Public.class)
    private List<CustomerDto> customers;

    @Schema(description = "Номер страницы")
    @JsonView(View.Public.class)
    private Integer pageNumber;

    @Schema(description = "Размер страницы")
    @JsonView(View.Public.class)
    private Integer pageSize;

    @Schema(description = "Количество найденных пользователей")
    @JsonView(View.Public.class)
    private Long totalCustomers;
}
