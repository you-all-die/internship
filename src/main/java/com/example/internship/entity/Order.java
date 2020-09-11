package com.example.internship.entity;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String customerFirstName;
    public String customerLastName;
//    public String address;
    public String customerMiddleName;
    public String customerEmail;
    public String customerPhone;
    public Long customerId;
    public Long statusId;

//    public List<OrderLine> orderLines;
//    public Long productId;
//    public Integer productQuantity;
//    public Long productCategory;
//    public String productName;
//    public String productDescription;
//    public String productPicture;
//    public Long publicPrice;


//    public Order(Customer customer) {
//        this.customerFirstName = customer.getFirstName();
//        this.customerLastName = customer.getLastName();
////        this.address = address;
//        this.customerMiddleName = customer.getMiddleName();
//        this.customerEmail = customer.getEmail();
//        this.customerPhone = customer.getPhone();
//        this.customerId = customer.getId();
//        this.statusId = 123L;
////        this.orderLines = customer.getCart().getOrderLines();
////        this.productId = orderLine.getProduct().getId();
////        this.productQuantity = orderLine.getProductQuantity();
//    }
}
