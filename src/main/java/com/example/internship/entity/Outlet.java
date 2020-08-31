package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Entity
@Table(name = "outlet")
@Data
public class Outlet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String city;

    private String name;

    private String address;

    private String phone;

    private String openingHours;

    private double longitude;

    private double latitude;
}
