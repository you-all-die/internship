package com.example.internship.controller.customer;

import com.example.internship.entity.Customer;
import com.example.internship.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //TODO: метод не нужен, по крайней мере здесь, возможно есть смысл заюзать в админке, но уже через рест апи
    @GetMapping("")
    public String viewCustomerList(Model model) {
        Iterable<Customer> customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer/index";
    }

    @GetMapping("/{id}")
    public String viewCustomerProfile(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.getById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer/view";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }

    //TODO: не используется
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

    //TODO: все таки стоит добавить что-то типа /save или /update
    @PostMapping("")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customer";
    }

    //TODO: не используется
    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customer";
    }
}
