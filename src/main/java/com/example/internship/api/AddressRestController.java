package com.example.internship.api;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import com.example.internship.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Роман каравашкин
 */

@RestController
@RequestMapping("/api/user/{customerId}/address")
@RequiredArgsConstructor
public class AddressRestController {


    private  final AddressService addressService;

    @GetMapping
    public ResponseEntity getAllCustomerById(@PathVariable Long customerId) {
        List<AddressDto> allById = addressService.getAllById(customerId);
        if (allById == null||allById.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allById);
    }

    @PostMapping
    public ResponseEntity addAddress(@PathVariable Long customerId, @RequestBody AddressDto addressDto) {
        addressService.addAddress(addressDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity deleteAddressById(@PathVariable Long customerId, @PathVariable Long addressId) {
         addressService.deleteAddress(customerId, addressId);
         return ResponseEntity.ok().build();
    }
}
