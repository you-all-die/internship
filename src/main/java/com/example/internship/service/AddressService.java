package com.example.internship.service;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;

import java.util.List;

/**
 * @author Роман Каравашкин
 */
public interface AddressService {
    List<AddressDto.Response.Full> getAllById(Long id);

    void addAddress(AddressDto.Request.Full addressDto);

    List<AddressDto.Response.Full> deleteAddress(Long id, Long addressId);
}
