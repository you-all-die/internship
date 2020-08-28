package com.example.internship.dto;

import com.example.internship.entity.Category;
import com.example.internship.entity.ProductStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private String picture;
    private BigDecimal price;
    private ProductStatus status;
}
