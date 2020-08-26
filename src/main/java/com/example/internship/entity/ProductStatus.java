package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_status")
@Data
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
}