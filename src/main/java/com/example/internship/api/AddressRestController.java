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
@RequestMapping("/api/user/{id}/address")
@RequiredArgsConstructor
public class AddressRestController {


    private  final AddressService addressService;

    @GetMapping
    public List<AddressDto.Response.Full> getAllCustomerById(@PathVariable Long id) {
        return addressService.getAllById(id);
    }

    @PostMapping
    public void addAddress(@RequestBody AddressDto.Request.Full addressDto) {
        addressService.addAddress(addressDto);
    }

    @DeleteMapping("/{addressId}")
    public List<AddressDto.Response.Full> deleteAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        return addressService.deleteAddress(id, addressId);
    }
}
