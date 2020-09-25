package com.example.internship.api.from;

import com.example.internship.refactoringdto.CustomerDto;
import com.example.internship.refactoringdto.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
public class CustomerSearchFrom {

    @Schema(description = "Пользователи")
    @JsonView(View.Public.class)
    private List<CustomerDto> customers;

    @Schema(description = "Номер страницы")
    @JsonView(View.Public.class)
    private Integer pageNumber;

    @Schema(description = "Размер страницы")
    @JsonView(View.Public.class)
    private Integer pageSize;

    @Schema(description = "Всего пользователей")
    @JsonView(View.Public.class)
    private Long totalCustomers;
}
