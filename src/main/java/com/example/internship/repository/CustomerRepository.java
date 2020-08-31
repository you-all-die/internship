package com.example.internship.repository;

import com.example.internship.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public Customer findByEmail(String email);
}
