package com.example.internship.service;

import com.example.internship.dto.ProductDto;

import java.util.List;


public interface ProductService {

    List<ProductDto> findAll();

    void removeProduct(Long id);

    void addProduct(ProductDto productDto);

    List<ProductDto> findByName(String name);
}
