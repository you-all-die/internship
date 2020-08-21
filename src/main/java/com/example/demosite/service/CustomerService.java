package com.example.demosite.service;

import com.example.demosite.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerService {
    @Autowired
    private CustomerService customerService;

    public final Iterable<Customer> getAll() {
        return customerService.getAll();
    }

    public final Customer getById(final long id) {
        return customerService.getById(id);
    }
}
