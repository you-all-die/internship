package com.example.internship.dto;

import com.example.internship.dto.product.ProductDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Data
@Component
public class ProductSearchResult {
    private List<ProductDto.Response.All> products;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalProducts;
}
