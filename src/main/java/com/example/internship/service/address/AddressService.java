package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto.ForList;
import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAllById(Long id);
    void addAddress(AddressDto addressDto);
    void deleteAddress(Long id, Long addressId);
    List<ForList> getAllByCustomerId(Long customerId);
    Address save(com.example.internship.dto.address.AddressDto.ForEditor addressDto);
}
