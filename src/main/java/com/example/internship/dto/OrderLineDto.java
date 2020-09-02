package com.example.internship.dto;

import lombok.*;

/**
 * @author Modenov D.A
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDto {

    private Long id;
    private ProductDto product;
    private Integer productQuantity;
}
