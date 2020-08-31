package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Entity
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private String region;
    private String city;
    private String district;
    private String street;
    private String house;
    private String apartment;
    private String Comment;
}