package com.example.internship.service.dadata;

import com.kuliginstepan.dadata.client.domain.Suggestion;
import com.kuliginstepan.dadata.client.domain.address.Address;
import reactor.core.publisher.Flux;

public interface DadataService {

    Flux<Suggestion<Address>> getSuggestionForAddress(String query);
}
