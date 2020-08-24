package com.example.demosite.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Category category;
//    @OneToMany(mappedBy = "category")
//    private Product product;
    private String name;
    @OneToMany(mappedBy = "id")
    private Category parent;

}
