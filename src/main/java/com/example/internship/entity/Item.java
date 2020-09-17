package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author  Sergey Lapshin
 */

@Entity
@Data
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order order;

    public Integer itemQuantity;
    public Long itemCategoryId;
    public String itemDescription;
    public String itemName;
    public String itemPicture;
    public BigDecimal itemPrice;



}
