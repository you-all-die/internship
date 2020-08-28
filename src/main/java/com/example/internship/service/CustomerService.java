package com.example.internship.service;

import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    // создание нового анонимного покупателя
    public Long createAnonymousCustomer() {
        return customerRepository.save(new Customer()).getId();
    }
}
