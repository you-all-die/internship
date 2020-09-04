package com.example.internship.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Data
@Component
public class ProductSearchResult {
    private List<ProductDto> products;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalProducts;
}
