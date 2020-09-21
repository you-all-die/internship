package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto.Response.ForList;
import com.example.internship.dto.addressDto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAllById(Long id);
    void addAddress(AddressDto addressDto);
    void deleteAddress(Long id, Long addressId);
    List<ForList> getAllByCustomerId(Long customerId);
}
