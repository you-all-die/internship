package com.example.internship.api;

import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.refactoringdto.View;
import com.example.internship.service.address.AddressService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ApiOperation(value = "Получение всех адресов пользователя")
    @Schema(implementation = AddressDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Адреса найдены"),
            @ApiResponse(code = 404, message = "Адреса не найдены")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<List<AddressDto>> getAllAddressesByCustomerId(@ApiParam(value = "Идентификатор пользователя")
                                                                        @PathVariable Long customerId) {

        List<AddressDto> allById = addressService.getAllByCustomerId(customerId);

        return allById == null || allById.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(allById);
    }

    /**
     * POST запрос, добавляет новый адрес для покупателя.
     *
     * @param customerId идентификатор покупателя
     * @param address    новый адрес
     * @return http status 200, если адрес добавлен, иначе http status 400
     */
    @PostMapping
    @ApiOperation(value = "Добавление адреса пользователя")
    @Schema(implementation = AddressDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Адреса добавлен"),
            @ApiResponse(code = 400, message = "Неправильные данные")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<AddressDto> addAddressToCustomer(@ApiParam(value = "Идентификатор пользователя")
                                                           @PathVariable Long customerId,
                                                           @ApiParam(value = "Данные адреса") @JsonView(View.Update.class)
                                                           @Valid @RequestBody AddressDto address,
                                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        address.setCustomerId(customerId);

        AddressDto addressDto = addressService.addAddressToCustomer(address);

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
    @ApiOperation(value = "Удаление адреса у пользователя")
    @Schema(implementation = AddressDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Адрес удален"),
            @ApiResponse(code = 400, message = "Неправильные данные")
    })
    @JsonView(View.Public.class)
    public ResponseEntity<?> deleteAddressFromCustomerByIds(@ApiParam(value = "Идентификатор пользователя")
                                                            @PathVariable Long customerId,
                                                            @ApiParam(value = "Идентификатор адреса")
                                                            @PathVariable Long addressId) {

        return addressService.deleteAddressFromCustomerByIds(customerId, addressId) ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}
