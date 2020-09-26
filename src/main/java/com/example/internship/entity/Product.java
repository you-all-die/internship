package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
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

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ProductStatus status;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;
}
