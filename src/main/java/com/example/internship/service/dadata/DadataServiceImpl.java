package com.example.internship.service.dadata;

import com.example.internship.dto.dadata.DadataAddressDto.Response.ValueOnly;
import com.kuliginstepan.dadata.client.DadataClient;
import com.kuliginstepan.dadata.client.domain.Suggestion;
import com.kuliginstepan.dadata.client.domain.address.Address;
import com.kuliginstepan.dadata.client.domain.address.AddressRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DadataServiceImpl implements DadataService {

    private final DadataClient dadataClient;
    private final ModelMapper mapper;

    @Override
    public List<ValueOnly> getSuggestionForAddress(String query) {
        return dadataClient
                .suggestAddress(AddressRequestBuilder.create(query).build())
                .map(this::convertToValueOnly)
                .collectList()
                .block();
    }

    private ValueOnly convertToValueOnly(Suggestion<Address> addressSuggestion) {
        return mapper.map(addressSuggestion, ValueOnly.class);
    }
}
