package com.example.internship.refactoringdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * @author Ivan Gubanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressShopDto {

    @Schema(description = "id магазина")
    private Long shopId;

    @Schema(description = "Адрес магазина")
    @Size(max = 256)
    private String address;

    @Schema(description = "Время работы")
    @Size(max = 64)
    private String schedule;
}
