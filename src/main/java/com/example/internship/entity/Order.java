package com.example.internship.entity;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.mapping.Set;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private Long statusId;

    @OneToMany(mappedBy = "id")
    private List<Item> items;
}
