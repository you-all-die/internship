package com.example.internship.api;

import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.refactoringdto.View;
import com.example.internship.service.address.AddressService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Роман каравашкин
 * <p>
 * Refactoring by Ivan Gubanov 25.09.20
 */

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
@RequiredArgsConstructor
public class AddressRestController {

    private final AddressService addressService;

    /**
     * GET запрос, возвращает все адреса покупателя.
     *
     * @param customerId идентификатор покупателя
     * @return возвращает список всех адресов покупателя если они есть, иначе http status 404
     */
    @GetMapping
    @Operation(description = "Получение всех адресов пользователя")
    @Schema(implementation = AddressDto.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адреса найдены"),
            @ApiResponse(responseCode = "404", description = "Адреса не найдены")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<List<AddressDto>> getAllAddressesByCustomerId(@Parameter(description = "Идентификатор пользователя")
                                                                            @PathVariable Long customerId) {

        List<AddressDto> allById = addressService.getAllByCustomerId(customerId);

        return allById == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(allById);
    }

    /**
     * POST запрос, добавляет новый адрес для покупателя.
     *
     * @param customerId идентификатор покупателя
     * @param address    новый адрес
     * @return http status 200, если адрес добавлен, иначе http status 400
     */
    @PostMapping
    @Operation(description = "Добавление адреса пользователя")
    @Schema(implementation = AddressDto.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адреса добавлен"),
            @ApiResponse(responseCode = "400", description = "Неправильные данные")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<?> addAddressToCustomer(@Parameter(description = "Идентификатор пользователя")
                                                  @PathVariable Long customerId,
                                                  @Parameter(description = "Данные адреса") @JsonView(View.Update.class)
                                                  @Valid @RequestBody AddressDto address,
                                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(e -> errors.append(e.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        AddressDto addressDto = addressService.addAddressToCustomer(customerId, address);

        return addressDto != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * DELETE запрос, удаляет адрес у покупателя.
     *
     * @param customerId идентификатор покупателя
     * @param addressId  идентификатор адреса
     * @return http status 200, если адрес удален, иначе http status 400
     */
    @DeleteMapping("/{addressId}")
    @Operation(description = "Удаление адреса у пользователя")
    @Schema(implementation = AddressDto.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адрес удален"),
            @ApiResponse(responseCode = "400", description = "Неправильные данные")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<?> deleteAddressFromCustomerByIds(@Parameter(description = "Идентификатор пользователя")
                                                            @PathVariable Long customerId,
                                                            @Parameter(description = "Идентификатор адреса")
                                                            @PathVariable Long addressId) {

        return addressService.deleteAddressFromCustomerByIds(customerId, addressId) ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}
