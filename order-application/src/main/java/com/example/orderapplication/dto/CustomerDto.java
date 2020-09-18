package com.example.orderapplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Modenov D.A
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Schema(description = "id пользователя")
    @NotNull
    private Long id;

    @Schema(description = "Имя")
    @NotNull
    private String firstName;

    @Schema(description = "Фамилия")
    @NotNull
    private String lastName;

    @Schema(description = "Отчество")
    private String middleName;

    @Schema(description = "Телефон")
    @NotNull
    private String phone;

    @Schema(description = "Почта")
    @NotNull
    @Email
    private String email;
}
