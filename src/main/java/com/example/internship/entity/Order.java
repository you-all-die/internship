package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author  Sergey Lapshin
 */

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Данные пользователя
    private Long customerId;
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;

//    Адрес
    private String addressRegion;
    private String addressCity;
    private String addressDistrict;
    private String addressStreet;
    private String addressHouse;
    private String addressApartment;
    private String addressComment;

//    Статус заказа (для процессинга)
    private String status;

    @OneToMany(
            mappedBy = "id",
            cascade = CascadeType.ALL
    )
    private List<Item> items;
    private Timestamp date;
}
