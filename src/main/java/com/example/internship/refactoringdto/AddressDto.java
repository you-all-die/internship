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
public class AddressDto {

    @Schema(description = "id адреса")
    @JsonView(View.All.class)
    private Long id;

    @Schema(description = "id покупателя")
    @JsonView(View.Public.class)
    @NotNull
    private Long customerId;

    @Schema(description = "Регион")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String region;

    @Schema(description = "Город")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String city;

    @Schema(description = "Район")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String district;

    @Schema(description = "Улица")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String street;

    @Schema(description = "Дом")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String house;

    @Schema(description = "Квартира")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String apartment;

    @Schema(description = "Комментарий")
    @JsonView(View.Public.class)
    @Size(max = 255)
    private String comment;
}
