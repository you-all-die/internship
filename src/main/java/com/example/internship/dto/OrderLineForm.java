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
public class OrderLineForm {

    Long productId;
    Integer productQuantity;
}
