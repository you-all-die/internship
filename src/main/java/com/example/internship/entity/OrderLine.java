package com.example.internship.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * @author Modenov D.A
 */
@Entity
@Table(name = "order_line")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    private Integer productQuantity;
}
