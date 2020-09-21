package com.example.internship.repository;

import com.example.internship.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Роман Каравашкин
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByCustomerId(Long userId);
}
