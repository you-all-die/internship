package com.example.internship.repository;

import com.example.internship.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public  interface CustomerRepository extends CrudRepository<Customer, Long> {
    public Customer findByEmail(String email);


    //Поиск пользователя по: ФИО, почта
    @Query(value = "SELECT * FROM Customers users " +
            "WHERE LOWER (users.first_name)  LIKE  %:firstName% " +
              "AND LOWER (users.middle_name)  LIKE  %:middleName% " +
              "AND LOWER (users.last_name)  LIKE  %:lastName%  " +
              "AND LOWER (users.email)  LIKE  %:email% ", nativeQuery = true)
    List<Customer> searchUsers(@Param("firstName") String firstName, @Param("middleName") String middleName,
                               @Param("lastName") String lastName,  @Param("email") String email);

}
