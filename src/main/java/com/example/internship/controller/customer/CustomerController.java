package com.example.internship.controller.customer;

import com.example.internship.entity.Customer;
import com.example.internship.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String customerList(Model model) {
        Iterable<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer/index";
    }
}
