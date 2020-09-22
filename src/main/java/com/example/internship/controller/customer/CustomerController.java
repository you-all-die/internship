package com.example.internship.controller.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String viewCustomerList(Model model) {
        Iterable<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer/index";
    }

    //TODO: Удалить при рефакторинге PathVariable id;
    @GetMapping("/{id}")
    public String viewCustomerProfile(@PathVariable Long id, Authentication authentication, Model model) {
        Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);

        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer/view";
        }

        throw new EntityNotFoundException("Customer not found");
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/profile";
    }

    @GetMapping("/{id}/edit")
    public String editCustomer(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.getById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer/profile";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }

    @PostMapping("")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customer";
    }
}
