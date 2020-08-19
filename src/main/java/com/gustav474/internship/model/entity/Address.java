package com.gustav474.internship.model.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Embeddable
@Table(name="ADDRESSES")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String address;

    public Address(String city, String address) {
        this.city = city;
        this.address = address;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
