package com.example.internship.service;

import com.example.internship.entity.Address;
import com.example.internship.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Iterable<Address> findAllByCustomerId(long customerId) {
        return addressRepository.findAllByCustomerId(customerId);
    }

    public Optional<Address> findById(long id) {
        return addressRepository.findById(id);
    }

    public void save(Address address) {
        addressRepository.save(address);
    }

    public void delete(long id) {
        addressRepository.deleteById(id);
    }
}
