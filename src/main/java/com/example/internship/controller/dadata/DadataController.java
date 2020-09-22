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
@RequestMapping("/dadata")
@RequiredArgsConstructor
public class DadataController {

    private final DadataService dadataService;

    @GetMapping("/address")
    public List<ValueOnly> getSuggestionForAddress(@RequestParam String query) {
        return dadataService.getSuggestionForAddress(query);
    }
}
