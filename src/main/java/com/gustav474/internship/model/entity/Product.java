package com.gustav474.internship.model.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private Float price;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> ordersByProducts;

    public Product(String name, String description, Float price, Set<OrderProduct> ordersByProducts) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ordersByProducts = ordersByProducts;
    }

    public Product() {
    }

    public Set<OrderProduct> getOrdersByProducts() {
        return ordersByProducts;
    }

    public void setOrdersByProducts(Set<OrderProduct> ordersByProducts) {
        this.ordersByProducts = ordersByProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
