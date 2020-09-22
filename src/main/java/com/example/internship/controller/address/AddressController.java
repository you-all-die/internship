package com.example.internship.controller.address;

import com.example.internship.entity.Customer;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(AddressController.BASE_MAPPING)
@RequiredArgsConstructor
public class AddressController {

    public static final String BASE_MAPPING = "/address";
    public static final String CREATE_ADDRESS_MAPPING = "/create";

    private final CustomerService customerService;

    @Value("${dadata.client.token}")
    private String token;

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
        model.addAttribute("token", token);
        return "/address/editor";
    }

}
