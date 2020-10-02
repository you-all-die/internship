package com.example.internship.api;

import com.example.internship.api.dto.CustomerSearchResponse;
import com.example.internship.refactoringdto.CustomerDto;
import com.example.internship.refactoringdto.View;
import com.example.internship.service.customer.CustomerService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Romodin Aleksey
 * <p>
 * Refactoring by Danil Movenov 25.09.20
 */

@RestController
@RequestMapping("/api/customers/")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;

    /**
     * GET запрос, возвращает данные пользователя по id.
     *
     * @param id идентификатор пользователя
     * @return http status 200 и пользователя или http status 400, если пользователь не найден
     */
    @GetMapping("{id}")
    @Operation(description = "Получение данных пользователя по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @Schema(implementation = CustomerDto.class)
    @JsonView(View.Public.class)
    public ResponseEntity<CustomerDto> getById(@Parameter(description = "Идентификатор пользователя")
                                               @PathVariable("id") Long id) {

        CustomerDto customer = customerService.getByIdRef(id);

        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    /**
     * GET запрос, поиск пользователя по критериям.
     *
     * @param firstName  имя
     * @param middleName отчество
     * @param lastName   фамилия
     * @param email      email
     * @param pageSize   размер страницы
     * @param pageNumber номер страницы
     * @return пользователь по критериям
     */
    @GetMapping("search")
    @Operation(description = "Поиск пользователей")
    @Schema(implementation = CustomerSearchResponse.class)
    @ApiResponse(responseCode = "200", description = "Поиск успешен")
    @JsonView(View.Public.class)
    public ResponseEntity<CustomerSearchResponse> searchUser(@Parameter(description = "Поиск по имени")
                                                         @RequestParam(name = "firstName", required = false) String firstName,
                                                             @Parameter(description = "Поиск по отчеству")
                                                         @RequestParam(name = "middleName", required = false) String middleName,
                                                             @Parameter(description = "Поиск по фамилии")
                                                         @RequestParam(name = "lastName", required = false) String lastName,
                                                             @Parameter(description = "Поиск по email")
                                                         @RequestParam(name = "email", required = false) String email,
                                                             @Parameter(description = "Размер страницы")
                                                         @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                             @Parameter(description = "Номер страницы")
                                                         @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber) {

        CustomerSearchResponse searchFrom = customerService.search(firstName, middleName, lastName, email, pageSize, pageNumber);

        return ResponseEntity.ok(searchFrom);
    }

    /**
     * PUT запрос, редактирование данных пользователя.
     *
     * @param id       идентификатор пользователя.
     * @param customer данные пользователя для редактирования.
     * @return http status 200 если изменения успешны, http status 400, если пользователь не найден
     * или не удается изменить данные.
     */
    @PutMapping("{id}")
    @Operation(description = "Редактирование данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлен"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @Schema(implementation = CustomerDto.class)
    @JsonView(View.Public.class)
    public ResponseEntity<?> updateUser(@Parameter(description = "Идентификатор пользователя")
                                                  @PathVariable("id") Long id,
                                                  @Parameter(description = "Данные для редактирования") @JsonView(View.Update.class)
                                                  @Valid @RequestBody CustomerDto customer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(e -> errors.append(e.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        CustomerDto customerDto = customerService.update(id, customer);

        return customerDto != null ? ResponseEntity.ok(customerDto) : ResponseEntity.notFound().build();
    }

}
