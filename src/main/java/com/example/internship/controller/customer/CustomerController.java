package com.example.internship.controller.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Customer;
import com.example.internship.service.address.AddressService;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/{id}/edit")
    public String editCustomer(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.getById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return BASE_MAPPING + "/profile";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }

    @PostMapping("")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:" + BASE_MAPPING;
    }

    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/" + BASE_MAPPING;
    }
}
