package com.example.adminapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Ivan Gubanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty
    private CategoryDto category;
    @Size(max = 64)
    private String name;
    @Size(max = 64)
    private String description;
    @Size(max = 64)
    private String picture;
    private BigDecimal price;
    @NotEmpty
    private ProductStatusDto status;
}
