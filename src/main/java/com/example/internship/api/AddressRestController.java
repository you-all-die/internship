package com.example.internship.api;

import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<AddressDto>> getAllAddressesByCustomerId(@PathVariable Long customerId) {

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
    public ResponseEntity<AddressDto> addAddressToCustomer(@PathVariable Long customerId,
                                                           @RequestBody AddressDto address) {

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
    public ResponseEntity<?> deleteAddressFromCustomerByIds(@PathVariable Long customerId,
                                                            @PathVariable Long addressId) {

        return addressService.deleteAddressFromCustomerByIds(customerId, addressId) ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}
