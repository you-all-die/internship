package com.example.internship.service;

import com.example.internship.entity.Customer;
import com.example.internship.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public final Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    public final Optional<Customer> getById(long id) {
        return customerRepository.findById(id);
    }

    // создание нового анонимного покупателя
    public Long createAnonymousCustomer() {
        return customerRepository.save(new Customer()).getId();
    }
}
