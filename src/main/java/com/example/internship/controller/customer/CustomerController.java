package com.example.internship.controller.customer;

import com.example.internship.dto.customer.SearchResult;
import com.example.internship.entity.Customer;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping(CustomerController.BASE_MAPPING)
@RequiredArgsConstructor
public class CustomerController {

    public final static String BASE_MAPPING = "/customer";

    private final CustomerService customerService;

    @GetMapping
    public String viewCustomerList(
            Model model,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Boolean ascendingOrder
    ) {
        final SearchResult data = customerService.findByCriteria(
                searchString,
                pageNumber,
                pageSize,
                ascendingOrder
        );
        model.addAttribute("data", data);
        return BASE_MAPPING + "/index";
    }

    @GetMapping("/{id}")
    public String viewCustomerProfile(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.getById(id);
        model.addAttribute("customer", customer.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        ));
        return BASE_MAPPING + "/view";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return BASE_MAPPING + "/profile";
    }

    @GetMapping("/{id}/edit")
    public String editCustomer(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.getById(id);
        model.addAttribute("customer", customer.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
        ));
        return BASE_MAPPING + "/profile";
    }

    @PostMapping
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:" + BASE_MAPPING;
    }

    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:" + BASE_MAPPING;
    }
}
