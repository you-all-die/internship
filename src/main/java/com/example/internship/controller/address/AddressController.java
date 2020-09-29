package com.example.internship.controller.address;

import com.example.internship.dto.address.AddressDto;
import com.example.internship.dto.dadata.DadataAddressDto;
import com.example.internship.service.address.AddressService;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.dadata.DadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping(AddressController.BASE_MAPPING)
@RequiredArgsConstructor
public class AddressController {

    public static final String BASE_MAPPING = "/address";
    public static final String CREATE_ADDRESS_MAPPING = "/create";
    public static final String SAVE_ADDRESS_MAPPING = "/save";
    public static final String ADDRESS_SUGGESTIONS = "/suggest";

    private final CustomerService customerService;
    private final AddressService addressService;
    private final DadataService dadataService;

    @GetMapping(CREATE_ADDRESS_MAPPING)
    public String createAddressForCustomer(
            Model model,
            @RequestParam Long customerId
    ) {
        if (customerService.existsById(customerId)) {
            final AddressDto.ForEditor dto = new AddressDto.ForEditor();
            dto.setCustomerId(customerId);
            model.addAttribute("address", dto);
            return "/address/editor";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Customer with id %d is not exists.", customerId));
        }
    }

    @PostMapping(SAVE_ADDRESS_MAPPING)
    public String saveAddress(
            @ModelAttribute AddressDto.ForEditor addressDto
    ) {
        addressService.save(addressDto);
        return "redirect:/customer/" + addressDto.getCustomerId();
    }

    @PostMapping(ADDRESS_SUGGESTIONS)
    public String getAddressSuggestions(
            Model model,
            @RequestParam String query
    ) {
        final List<DadataAddressDto.ValueOnly> suggestions = dadataService.getAddressSuggestions(query);
        model.addAttribute("suggestions", suggestions);
        return "/address/suggestions :: widget";
    }
}
