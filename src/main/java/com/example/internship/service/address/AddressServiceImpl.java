package com.example.internship.service.address;

import com.example.internship.entity.Address;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.repository.AddressesRepository;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Роман Каравашкин
 * <p>
 * Refactoring by Ivan Gubanov 25.09.20
 */

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressesRepository addressesRepository;

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    @Override
    public List<AddressDto> getAllByCustomerId(Long customerId) {

        if (customerService.getByIdRef(customerId) != null) {

            return addressesRepository.findAddressByCustomerId(customerId).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public AddressDto addAddressToCustomer(AddressDto addressDto) {

        if (customerService.getByIdRef(addressDto.getCustomerId()) != null) {
            return convertToDto(addressesRepository.save(convertToEntity(addressDto)));
        }

        return null;
    }

    @Override
    public boolean deleteAddressFromCustomerByIds(Long customerId, Long addressId) {

        if (getAddressFromCustomerByIds(customerId, addressId) != null) {
            addressesRepository.deleteById(addressId);

            return true;
        }

        return false;
    }

    @Override
    public AddressDto getAddressFromCustomerByIds(Long customerId, Long addressId) {

        if (customerService.getByIdRef(customerId) != null) {
            Optional<Address> address = addressesRepository.findById(addressId);

            return address.map(this::convertToDto).orElse(null);
        }

        return null;
    }


    private AddressDto convertToDto(Address address) {

        return modelMapper.map(address, AddressDto.class);
    }

    private Address convertToEntity(AddressDto addressDto) {

        return modelMapper.map(addressDto, Address.class);
    }
}



