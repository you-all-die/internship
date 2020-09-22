package com.example.internship.controller.dadata;

import com.example.internship.dto.dadata.DadataAddressDto.Response.ValueOnly;
import com.example.internship.service.dadata.DadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(DadataController.BASE_MAPPING)
@RequiredArgsConstructor
public class DadataController {

    public static final String BASE_MAPPING = "/dadata";
    public static final String ADDRESS_MAPPING = "/address";

    private final DadataService dadataService;

    @GetMapping(ADDRESS_MAPPING)
    public List<ValueOnly> getSuggestionForAddress(@RequestParam String query) {
        return dadataService.getSuggestionForAddress(query);
    }
}
