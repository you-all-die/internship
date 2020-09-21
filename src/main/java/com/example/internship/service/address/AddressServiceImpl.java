package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto.Response.ForList;
import com.example.internship.dto.addressDto.AddressDto;
import com.example.internship.entity.Address;
import com.example.internship.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableList;

@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
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

    @Override
    public List<ForList> getAllByCustomerId(Long customerId) {
        return addressRepository.findAddressByCustomerId(customerId)
                .stream()
                .map(this::convertToForList)
                .collect(toUnmodifiableList());
    }


    private AddressDto convertToDto(Address address) {
        return mapper.map(address, AddressDto.class);
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
    }

    private Converter<Address, ForList> addressToForListConverter() {
        return context -> {
            Address address = context.getSource();
            ForList forList = context.getDestination();
            forList.setFullAddress(generateFullAddress(address));
            return forList;
        };
    }

    public final String generateFullAddress(Address address) {
        return List.of(
                address.getRegion(),
                address.getCity(),
                address.getDistrict(),
                address.getStreet(),
                address.getHouse(),
                address.getApartment()
        ).stream().filter(StringUtils::isNotBlank).collect(joining(" "));
    }
}
