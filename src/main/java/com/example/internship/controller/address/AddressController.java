package com.example.internship.controller.address;

import com.example.internship.controller.customer.CustomerController;
import com.example.internship.dto.CustomerDto;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.service.address.AddressService;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping(AddressController.BASE_MAPPING)
@RequiredArgsConstructor
public class AddressController {

    public static final String BASE_MAPPING = "/address";

    private final CustomerService customerService;
    private final AddressService addressService;

    @GetMapping("/add")
    public String addShippingAddress(
            Authentication authentication,
            Model model
    ) {
        final Optional<CustomerDto> customerDtoOptional = customerService.getFromAuthentication(authentication);
        if (customerDtoOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer not found.");
        }
        final CustomerDto customerDto = customerDtoOptional.get();
        final AddressDto addressDto = new AddressDto();
        addressDto.setCustomerId(customerDto.getId());
        model.addAttribute("address", addressDto);
        return "/address/edit";
    }

    @PostMapping("/save")
    public String saveShippingAddress(
            @ModelAttribute AddressDto addressDto
    ) {
        addressService.save(addressDto);
        return "redirect:" + CustomerController.BASE_MAPPING;
    }
}
