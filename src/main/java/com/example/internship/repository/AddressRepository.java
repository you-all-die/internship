package com.example.internship.repository;

import com.example.internship.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Iterable<Address> findAllByCustomerId(long customerId);
}
