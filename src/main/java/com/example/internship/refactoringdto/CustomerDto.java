package com.example.internship.refactoringdto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
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
@ToString
public class CustomerDto {

    @Schema(description = "id покупателя")
    @JsonView({View.Public.class, View.Private.class, View.All.class})
    private Long id;

    @Schema(description = "Имя")
    @JsonView({View.Public.class, View.Update.class})
    @Size(max = 64)
    private String firstName;

    @Schema(description = "Отчество")
    @JsonView({View.Public.class, View.Update.class})
    @Size(max = 64)
    private String middleName;

    @Schema(description = "Фамилия")
    @JsonView({View.Public.class, View.Update.class})
    @Size(max = 64)
    private String lastName;

    @Schema(description = "Пароль")
    @JsonView(View.Private.class)
    @Size(max = 60)
    private String password;

    @Schema(description = "Телефон")
    @JsonView({View.Public.class, View.Update.class})
    @Size(max = 64)
    private String phone;

    @Schema(description = "Почта")
    @JsonView({View.Public.class, View.Update.class})
    @Email
    @Size(max = 64)
    private String email;

    @Schema(description = "Список адресов")
    @JsonView(View.All.class)
    private Set<AddressDto> addresses;

    @Schema(description = "Корзина")
    @JsonView(View.All.class)
    private CartDto cart;
}
