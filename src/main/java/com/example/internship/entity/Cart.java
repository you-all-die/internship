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

    @OneToOne
    @JoinColumn
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

}
