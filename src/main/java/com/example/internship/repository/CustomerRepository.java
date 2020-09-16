package com.example.internship.repository;

import com.example.internship.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public  interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.lastActivity = NOW() WHERE c.id = :id")
    int setLastActivityForCustomers(@Param(value = "id") Long customerId);

    @Transactional
    @Modifying
    @Query("DELETE Customer c WHERE c.firstName = NULL AND c.email = NULL AND c.lastActivity < YESTERDAY()")
    int deleteInactiveAnonymousUsers();
}
