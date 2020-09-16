package com.example.internship.service;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Роман Каравашкин
 */
public interface AddressService {
    List<AddressDto> getAllById(Long id);

    void addAddress(AddressDto addressDto);

    void deleteAddress(Long id, Long addressId);
}
