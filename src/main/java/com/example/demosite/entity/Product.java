package com.example.demosite.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String description;

    private String picture;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ProductStatus status;
}
