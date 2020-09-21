package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto;
import com.example.internship.entity.Address;

import java.util.List;

public interface AddressService {
    List<AddressDto.Response.ForList> getAllByCustomerId(Long customerId);
    String generateFullAddress(Address address);
}
