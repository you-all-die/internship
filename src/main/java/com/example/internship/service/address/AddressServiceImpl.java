package com.example.internship.service.address;

import com.example.internship.dto.address.AddressDto.Response.ForList;
import com.example.internship.entity.Address;
import com.example.internship.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

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

    private String generateFullAddress(Address address) {
        return new StringJoiner(", ")
                .add(address.getRegion())
                .add(address.getCity())
                .add(address.getDistrict())
                .add(address.getStreet())
                .add(address.getHouse())
                .add(address.getApartment())
                .toString();
    }
}
