package com.example.internship.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Роман Каравашкин.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "product_rate")
public class ProductRating {
        @Id
        private Long id;
        @Column(name = "product_id")
        private Long productId;
        @Column(name = "customer_id")
        private Long customerId;
        @Column(name = "rating")
        private Long rating;
}
