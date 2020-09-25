package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonView(View.All.class)
    private Long id;

    @Schema(description = "Имя")
    @JsonView(View.NoId.class)
    @Size(max = 64)
    private String firstName;

    @Schema(description = "Отчество")
    @JsonView(View.NoId.class)
    @Size(max = 64)
    private String middleName;

    @Schema(description = "Фамилия")
    @JsonView(View.NoId.class)
    @Size(max = 64)
    private String lastName;

    @Schema(description = "Пароль")
    @JsonView(View.NoId.class)
    @Size(max = 60)
    private String password;

    @Schema(description = "Телефон")
    @JsonView(View.NoId.class)
    @Size(max = 64)
    private String phone;

    @Schema(description = "Почта")
    @JsonView(View.NoId.class)
    @Size(max = 64)
    private String email;

    @Schema(description = "Список адресов")
    @JsonView(View.NoId.class)
    private Set<AddressDto> addresses;

    @Schema(description = "Корзина")
    @JsonView(View.NoId.class)
    private CartDto cart;
}
