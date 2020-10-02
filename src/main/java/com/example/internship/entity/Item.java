package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author  Sergey Lapshin
 */

@Entity
@Data
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer itemQuantity;
    private Long itemCategoryId;
    private String itemDescription;
    private String itemName;
    private String itemPicture;
    private BigDecimal itemPrice;

}
