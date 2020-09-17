package com.example.internship.repository;

import com.example.internship.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public  interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Customer findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update customers last_activity set last_activity = now() " +
            "where id = :id and EXTRACT(EPOCH FROM(now() - last_activity)) > 3600",
            nativeQuery = true)
    void setLastActivityForCustomers(@Param("id") Long customerId);

    @Transactional
    @Modifying
    @Query(value = "delete FROM customers where first_name is null AND (last_activity < 'yesterday' OR last_activity is null)",
            nativeQuery = true)
    Integer deleteInactiveAnonymousUsers();
}
