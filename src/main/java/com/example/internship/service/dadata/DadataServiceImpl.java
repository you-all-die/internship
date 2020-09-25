package com.example.internship.service.dadata;

import com.example.internship.dto.dadata.DadataAddressDto;
import com.kuliginstepan.dadata.client.DadataClient;
import com.kuliginstepan.dadata.client.domain.Suggestion;
import com.kuliginstepan.dadata.client.domain.address.Address;
import com.kuliginstepan.dadata.client.domain.address.AddressRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class DadataServiceImpl implements DadataService {

    private final DadataClient dadataClient;
    private final ModelMapper modelMapper;

    @Override
    public List<DadataAddressDto.ValueOnly> getAddressSuggestions(String query) {
        if (StringUtils.isBlank(query)) {
            return emptyList();
        }
        return Objects.requireNonNull(dadataClient.suggestAddress(AddressRequestBuilder.create(query).build())
                .collectList()
                .block())
                .stream()
                .map(this::convertSuggestionAddressToValueOnly)
                .collect(toUnmodifiableList());
    }

    private DadataAddressDto.ValueOnly convertSuggestionAddressToValueOnly(Suggestion<Address> addressSuggestion) {
        return modelMapper.map(addressSuggestion, DadataAddressDto.ValueOnly.class);
    }
}
