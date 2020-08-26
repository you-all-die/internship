package com.example.demosite.entity;

import lombok.Data;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String phone;
    private String email;
    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;
}
