package com.example.demosite.service;

import com.example.demosite.entity.Customer;
import com.example.demosite.repository.CustomerRepository;
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

    public final Optional<Customer> getById(final long id) {
        return customerRepository.findById(id);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public void delete(long id) {
        customerRepository.deleteById(id);
    }
}
