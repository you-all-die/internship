package com.example.adminapplication.service;

import com.example.adminapplication.dto.ProductDto;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
public interface ProductService {
    List<ProductDto> findAll();

    void removeProduct(Long id);

    void saveProduct(ProductDto product);

    List<ProductDto> findByName(String name);

    ProductDto findByIdProduct(Long id);
}
