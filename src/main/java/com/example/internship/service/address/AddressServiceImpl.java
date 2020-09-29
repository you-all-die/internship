package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto.ForEditor;
import com.example.internship.dto.address.AddressDto.ForList;
import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import com.example.internship.helper.JoinHelper;
import com.example.internship.repository.AddressRepository;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerService customerService;
    private final ModelMapper mapper;

    @Override
    public List<AddressDto> getAllById(Long id) {
        return addressRepository
                .findAddressByCustomerId(id)
                .stream()
                .map(this::convertToDto)
                .collect(toUnmodifiableList());
    }

    @Override
    public void addAddress(AddressDto addressDto) {
        addressRepository.save(mapper.map(addressDto, Address.class));
    }

    @Override
    public void deleteAddress(Long id, Long addressId) {
        addressRepository.deleteById(addressId);
    }

    private AddressDto convertToDto(Address address) {
        return mapper.map(address, AddressDto.class);
    }

    //============================================================================================================//

    @Override
    public Address save(ForEditor addressDto) {
        return addressRepository.save(mapper.map(addressDto, Address.class));
    }

    @Override
    public List<ForList> getAllByCustomerId(Long customerId) {
        return addressRepository.findAddressByCustomerId(customerId)
                .stream()
                .map(this::convertToForList)
                .collect(toUnmodifiableList());
    }

    private ForList convertToForList(Address address) {
        return mapper.map(address, ForList.class);
    }

    @PostConstruct
    private void configureAddressMapper() {
        mapper
                .createTypeMap(Address.class, AddressDto.class)
                .addMappings(mapper -> mapper.map(address -> address.getCustomer().getId(), AddressDto::setCustomerId));
        mapper
                .createTypeMap(Address.class, ForList.class)
                .addMappings(mapper -> mapper.skip(ForList::setFullAddress))
                .setPostConverter(addressToForListConverter());
        mapper
                .createTypeMap(ForEditor.class, Address.class)
                .addMappings(mapper -> mapper.skip(Address::setCustomer))
                .setPostConverter(fromEditorToAddressConverter());
    }

    private Converter<ForEditor, Address> fromEditorToAddressConverter() {
        return context -> {
            final ForEditor dto = context.getSource();
            final Address entity = context.getDestination();
            entity.setCustomer(customerService.getById(dto.getCustomerId()).orElseThrow());
            return entity;
        };
    }

    private Converter<Address, ForList> addressToForListConverter() {
        return context -> {
            Address address = context.getSource();
            ForList forList = context.getDestination();
            forList.setFullAddress(generateFullAddress(address));
            return forList;
        };
    }

    private String generateFullAddress(Address address) {
        return JoinHelper.join(" ",
                address.getRegion(),
                address.getCity(),
                address.getDistrict(),
                address.getStreet(),
                address.getHouse(),
                address.getApartment(),
                address.getComment()
        );
    }
}
