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
    @JoinColumn(name = "id")
    private Category categoryId;
    private String name;
    private String description;
    private String picture;
    @ManyToOne
    @JoinColumn(name = "id")
    private ProductStatus statusId;

}
