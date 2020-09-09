package com.example.internship.service.impl;

import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import com.example.internship.repository.AddressesRepository;
import com.example.internship.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<AddressDto.Response.Full> getAllById(Long customerId) {
        return addressesRepository.findAddressByCustomerId(customerId)
                .stream().map(this::convertToFullDto).collect(Collectors.toList());

    }

    @Override
    public void addAddress(AddressDto.Request.Full addressDto) {
        addressesRepository.save(modelMapper.map(addressDto, Address.class));
    }

    @Override
    public List<AddressDto.Response.Full> deleteAddress(Long id, Long addressId) {
        addressesRepository.deleteById(addressId);
        return addressesRepository.findAddressByCustomerId(id)
                .stream().map(this::convertToFullDto).collect(Collectors.toList());
    }


    private AddressDto.Response.Full convertToFullDto(Address address) {
        return modelMapper.map(address, AddressDto.Response.Full.class);
    }

    private Address convertToEntity(AddressDto.Request.Full addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.createTypeMap(Address.class, AddressDto.Response.Full.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getCustomer().getId(), AddressDto.Response.Full::setCustomerId)
                );

        modelMapper.createTypeMap(AddressDto.Request.Full.class, Address.class).addMappings(
                mapper -> mapper.<Long>map(AddressDto.Request.Full::getCustomerId,
                        (target, v) -> target.getCustomer().setId(v)));

    }
}



