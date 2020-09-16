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
    //@Procedure(procedureName = "update_last_activity")
//    @Query(value = "call update_last_activity(:id)",
//    nativeQuery = true)
    @Modifying
    @Query(value = "update customers last_activity set last_activity = now() " +
            "where id = :id and EXTRACT(EPOCH FROM(now() - last_activity)) > 86400",
            nativeQuery = true)
    void setLastActivityForCustomers(@Param("id") Long customerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE customers WHERE first_name = NULL AND email = NULL AND last_activity < YESTERDAY()",
            nativeQuery = true)
    int deleteInactiveAnonymousUsers();
}
