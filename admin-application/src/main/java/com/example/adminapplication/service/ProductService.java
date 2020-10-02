package com.example.adminapplication.service;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.dto.ProductSearchResult;

import java.math.BigDecimal;

/**
 * @author Ivan Gubanov
 */
public interface ProductService {

    void removeProduct(Long id);

    ProductDto saveProduct(ProductDto product);

    ProductDto findByIdProduct(Long id);

    ProductSearchResult productSearch(String searchText, Long categoryId, BigDecimal priceFrom, BigDecimal priceTo,
                                      Integer pageSize, Integer pageNumber);
}
