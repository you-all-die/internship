package com.example.internship.service.impl;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import com.example.internship.repository.AddressesRepository;
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

    private final AddressesRepository addressesRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<AddressDto> getAllById(Long customerId) {
        return addressesRepository.findAddressByCustomerId(customerId)
                .stream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public void addAddress(AddressDto addressDto) {
        addressesRepository.save(modelMapper.map(addressDto, Address.class));
    }

    @Override
    public List<AddressDto> deleteAddress(Long id, Long addressId) {
        addressesRepository.deleteById(addressId);
        return addressesRepository.findAddressByCustomerId(id)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }


    private AddressDto convertToDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    private Address convertToEntity(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Address.class, AddressDto.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getCustomer().getId(), AddressDto::setCustomerId)
                );

        modelMapper.createTypeMap(AddressDto.class, Address.class).addMappings(
                mapper -> mapper.<Long>map(AddressDto::getCustomerId,
                        (target, v) -> target.getCustomer().setId(v)));

    }
}



