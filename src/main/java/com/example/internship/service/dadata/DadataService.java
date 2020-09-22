package com.example.internship.service.dadata;

import com.example.internship.dto.dadata.DadataAddressDto.Response.ValueOnly;

import java.util.List;

public interface DadataService {

    List<ValueOnly> getSuggestionForAddress(String query);
}
