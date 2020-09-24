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
    @Size(max = 64)
    @JsonView(View.NoId.class)
    private String firstName;

    @Schema(description = "Отчество")
    @Size(max = 64)
    @JsonView(View.NoId.class)
    private String middleName;

    @Schema(description = "Фамилия")
    @Size(max = 64)
    @JsonView(View.NoId.class)
    private String lastName;

    @Schema(description = "Пароль")
    @Size(max = 60)
    @JsonView(View.NoId.class)
    private String password;

    @Schema(description = "Телефон")
    @Size(max = 64)
    @JsonView(View.NoId.class)
    private String phone;

    @Schema(description = "Почта")
    @Size(max = 64)
    @JsonView(View.NoId.class)
    private String email;

    @Schema(description = "Список адресов")
    @JsonView(View.NoId.class)
    private Set<AddressDto> addresses;

    @Schema(description = "Корзина")
    @JsonView({View.NoId.class, View.All.class})
    private CartDto cart;
}
