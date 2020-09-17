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
    public Long id;

//    Данные пользователя
    public Long customerId;
    public String customerFirstName;
    public String customerMiddleName;
    public String customerLastName;
    public String customerPhone;
    public String customerEmail;

//    Адрес
    public Long addressId;
    public String addressRegion;
    public String addressCity;
    public String addressDistrict;
    public String addressStreet;
    public String addressHouse;
    public String addressApartment;
    public String addressComment;

//    Статус заказа (для процессинга)
    public Long statusId;

    @OneToMany(mappedBy = "id")
    public List<Item> items;
}
