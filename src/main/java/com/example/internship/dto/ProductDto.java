package com.example.internship.dto;

import com.example.internship.entity.Feedback;
import com.example.internship.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private CategoryDto category;
    private String name;
    private String description;
    private String picture;
    private BigDecimal price;
    private ProductStatus status;
}
