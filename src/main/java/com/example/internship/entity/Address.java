package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String region;
    private String city;
    private String district;
    private String street;
    private String house;
    private String apartment;
    private String Comment;

    public final String getFullAddress() {
        return String.join(", ", region, city, district, street, house, apartment);
    }
}
