package com.example.adminapplication.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@Data
public class ProductSearchResult {

    private List<ProductDto> products;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalProducts;
    private Long totalPages;
    private Integer prevPage;
    private Integer nextPage;
    private Integer firstPage;
    private Integer lastPage;
}
