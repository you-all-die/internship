package com.example.internship.service.address;

import com.example.internship.entity.Address;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.repository.AddressesRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

        return addressesRepository.findAddressByCustomerId(customerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto) {

        return convertToDto(addressesRepository.save(convertToEntity(addressDto)));
    }

    @Override
    public boolean deleteAddress(Long id, Long addressId) {

        addressesRepository.deleteById(addressId);

        return addressesRepository.existsById(addressId);
    }


    private AddressDto convertToDto(Address address) {

        return modelMapper.map(address, AddressDto.class);
    }

    private Address convertToEntity(AddressDto addressDto) {

        return modelMapper.map(addressDto, Address.class);
    }

//    @PostConstruct
//    private void configureMapper() {
//        modelMapper.createTypeMap(Address.class, AddressDto.class)
//                .addMappings(mapper ->
//                        mapper.map(src -> src.getCustomerId(), AddressDto::setCustomerId)
//                );
//
//        modelMapper.createTypeMap(AddressDto.class, Address.class).addMappings(
//                mapper -> mapper.<Long>map(AddressDto::getCustomerId,
//                        (target, v) -> target.setCustomerId(v)));
//
//    }
}



