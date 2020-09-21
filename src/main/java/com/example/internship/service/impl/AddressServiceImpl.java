package com.example.internship.service.impl;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import com.example.internship.repository.AddressRepository;
import com.example.internship.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Роман Каравашкин
 */

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<AddressDto> getAllById(Long customerId) {
        return addressRepository.findAddressByCustomerId(customerId)
                .stream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public void addAddress(AddressDto addressDto) {
        addressRepository.save(modelMapper.map(addressDto, Address.class));

    }

    @Override
    public void deleteAddress(Long id, Long addressId) {
        addressRepository.deleteById(addressId);
        addressRepository.findAddressByCustomerId(id)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }


    private AddressDto convertToDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Address.class, AddressDto.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getCustomer().getId(), AddressDto::setCustomerId)
                );
    }
}



