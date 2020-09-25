package com.example.internship.controller.address;

import com.example.internship.dto.dadata.DadataAddressDto.ValueOnly;
import com.example.internship.entity.Customer;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.dadata.DadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping(AddressController.BASE_MAPPING)
@RequiredArgsConstructor
public class AddressController {

    public static final String BASE_MAPPING = "/address";
    public static final String CREATE_ADDRESS_MAPPING = "/create";
    public static final String ADDRESS_SUGGESTIONS = "/suggest";

    private final CustomerService customerService;
    private final DadataService dadataService;

    @GetMapping(CREATE_ADDRESS_MAPPING)
    public String createAddressForCustomer(
            Model model,
            @RequestParam Long customerId
    ) {
        final Customer customer = customerService.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Customer with id = %d not found", customerId)
                )
        );
        model.addAttribute("customerId", customer.getId());
        return "/address/editor";
    }

    @PostMapping(ADDRESS_SUGGESTIONS)
    public String getAddressSuggestions(
            Model model,
            @RequestParam String query
    ) {
        final List<ValueOnly> suggestions = dadataService.getAddressSuggestions(query);
        model.addAttribute("suggestions", suggestions);
        return "/address/suggestions :: widget";
    }
}
