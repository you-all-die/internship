package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;

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

    public final String getFullName() {
        return String.join(" ", lastName, firstName, middleName);
    }
}
