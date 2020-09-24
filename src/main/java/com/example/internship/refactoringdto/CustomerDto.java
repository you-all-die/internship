package com.example.internship.refactoringdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Ivan Gubanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDto {

    @Schema(description = "id покупателя")
    private Long id;

    @Schema(description = "Имя")
    @Size(max = 64)
    private String firstName;

    @Schema(description = "Отчество")
    @Size(max = 64)
    private String middleName;

    @Schema(description = "Фамилия")
    @Size(max = 64)
    private String lastName;

    @Schema(description = "Пароль")
    @Size(max = 60)
    private String password;

    @Schema(description = "Телефон")
    @Size(max = 64)
    private String phone;

    @Schema(description = "Почта")
    @Size(max = 64)
    private String email;

    @Schema(description = "Список адресов")
    private Set<AddressDto> addresses;

    @Schema(description = "Корзина")
    @NotNull
    private CartDto cart;
}
