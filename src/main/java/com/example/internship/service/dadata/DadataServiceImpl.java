package com.example.internship.service.dadata;

import com.kuliginstepan.dadata.client.DadataClient;
import com.kuliginstepan.dadata.client.domain.Suggestion;
import com.kuliginstepan.dadata.client.domain.address.Address;
import com.kuliginstepan.dadata.client.domain.address.AddressRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class DadataServiceImpl implements DadataService {

    private final DadataClient dadataClient;

    @Override
    public Flux<Suggestion<Address>> getSuggestionForAddress(String query) {
        return dadataClient.suggestAddress(AddressRequestBuilder.create(query).build());
    }
}
