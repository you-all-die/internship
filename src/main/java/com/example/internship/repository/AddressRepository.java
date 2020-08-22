package com.example.internship.repository;

import com.example.internship.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Iterable<Address> findAllByCustomerId(long customerId);
}
