package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto.Response.ForList> getAllByCustomerId(Long customerId);
}
