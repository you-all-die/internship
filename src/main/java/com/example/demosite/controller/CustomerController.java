package com.example.demosite.controller;

import com.example.demosite.entity.Customer;
import com.example.demosite.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String customerList(Model model) {
        model.addAttribute("customers", customerService.getAll());
        return "customer/list";
    }

    @GetMapping("/edit")
    public String editCustomer(@RequestParam(name = "id", required = true) long id, Model model) {
        Optional<Customer> customer = customerService.getById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer/profile";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, Model model) {
        customerService.save(customer);
        return "redirect:/customer/list";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam(name = "id", required = true) long id, Model model) {
        customerService.delete(id);
        return "redirect:/customer/list";
    }
}
