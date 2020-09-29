package com.example.internship.service.address;

import com.example.internship.entity.Address;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.repository.AddressesRepository;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

        if (Objects.isNull(customerService.getByIdRef(customerId))) {
            return null;
        }

        return addressesRepository.findAddressByCustomerId(customerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto addAddressToCustomer(Long customerId, AddressDto addressDto) {

        if (Objects.isNull(customerService.getByIdRef(customerId))) {
            return null;
        }
        addressDto.setCustomerId(customerId);

        return convertToDto(addressesRepository.save(convertToEntity(addressDto)));
    }

    @Override
    public boolean deleteAddressFromCustomerByIds(Long customerId, Long addressId) {

        AddressDto address = getAddressFromCustomerByIds(customerId, addressId);
        if (Objects.isNull(address) || !address.getCustomerId().equals(customerId)) {
            return false;
        }

        addressesRepository.deleteById(addressId);

        return true;
    }

    @Override
    public AddressDto getAddressFromCustomerByIds(Long customerId, Long addressId) {

        if (Objects.isNull(customerService.getByIdRef(customerId))) {
            return null;
        }

        Optional<Address> address = addressesRepository.findById(addressId);

        return address.map(this::convertToDto).orElse(null);
    }


    private AddressDto convertToDto(Address address) {

        return modelMapper.map(address, AddressDto.class);
    }

    private Address convertToEntity(AddressDto addressDto) {

        return modelMapper.map(addressDto, Address.class);
    }
}
