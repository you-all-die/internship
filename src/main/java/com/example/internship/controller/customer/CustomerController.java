package com.example.internship.controller.customer;

import com.example.internship.dto.CustomerDto;
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

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping(CustomerController.BASE_MAPPING)
@RequiredArgsConstructor
public class CustomerController {

    public static final String BASE_MAPPING = "/customer";

    private final CustomerService customerService;
    private final AddressService addressService;

    @GetMapping
    public String viewCustomerProfile(
            Authentication authentication,
            Model model
    ) {
        Optional<CustomerDto> customerOptional = customerService.getFromAuthentication(authentication);

        if (customerOptional.isPresent()) {
            CustomerDto customer = customerOptional.get();
            Long customerId = customer.getId();
            model
                    .addAttribute("customer", customer)
                    .addAttribute("addresses", addressService.getAllByCustomerId(customerId));
            return BASE_MAPPING + "/view";
        }

        throw new EntityNotFoundException("Customer not found");
    }

    @GetMapping("/edit")
    public String editCustomer(
            Authentication authentication,
            Model model
    ) {
        Optional<CustomerDto> customerOptional = customerService.getFromAuthentication(authentication);
        if (customerOptional.isPresent()) {
            CustomerDto customerDto = customerOptional.get();
            model.addAttribute("customer", customerDto);
            return BASE_MAPPING + "/profile";
        }
        throw new EntityNotFoundException("Customer not found");
    }

    @PostMapping
    public String saveCustomer(@ModelAttribute CustomerDto customer) {
        customerService.save(customer);
        return "redirect:" + BASE_MAPPING;
    }
}
