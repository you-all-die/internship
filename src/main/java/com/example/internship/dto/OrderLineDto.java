package com.example.internship.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Long id;
    private ProductDto product;
    private Integer productQuantity;
}
