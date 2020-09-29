package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class AddressShopDto {

    @Schema(description = "id магазина")
    @JsonView(View.All.class)
    private Long shopId;

    @Schema(description = "Адрес магазина")
    @JsonView(View.Public.class)
    @Size(max = 256)
    private String address;

    @Schema(description = "Время работы")
    @JsonView(View.Public.class)
    @Size(max = 64)
    private String schedule;
}
