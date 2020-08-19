package com.gustav474.internship.model.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> ordersByProducts;

    @Embedded
    private Address address;

    public Order(Set<OrderProduct> ordersByProducts, Address address) {
        this.ordersByProducts = ordersByProducts;
        this.address = address;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<OrderProduct> getOrdersByProducts() {
        return ordersByProducts;
    }

    public void setOrdersByProducts(Set<OrderProduct> ordersByProducts) {
        this.ordersByProducts = ordersByProducts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
