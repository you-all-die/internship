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
public class CartForm {

    Long productId;
    Integer productQuantity;
}
