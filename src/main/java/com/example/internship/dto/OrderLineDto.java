package com.example.internship.dto;

import com.example.internship.dto.product.ProductDto;
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
    private ProductDto.Response.All product;
    private Integer productQuantity;
}
