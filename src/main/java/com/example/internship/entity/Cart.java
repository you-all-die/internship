package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Modenov D.A
 */
@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cart_order_line",
            joinColumns = {@JoinColumn(name = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_line_id")})
    private List<OrderLine> orderLines;

}
