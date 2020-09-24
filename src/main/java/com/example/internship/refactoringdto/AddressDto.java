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
    @NotNull
    @JsonView(View.NoId.class)
    private Long customerId;

    @Schema(description = "Регион")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String region;

    @Schema(description = "Город")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String city;

    @Schema(description = "Район")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String district;

    @Schema(description = "Улица")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String street;

    @Schema(description = "Дом")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String house;

    @Schema(description = "Квартира")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String apartment;

    @Schema(description = "Комментарий")
    @Size(max = 255)
    @JsonView(View.NoId.class)
    private String comment;
}
