package com.example.internship.service;

import com.example.internship.dto.ProductDto;
import com.example.internship.dto.ProductSearchResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductDto> findAll();
    void removeProduct(Long id);
    void addProduct(ProductDto productDto);
    List<ProductDto> findByName(String name);

    List<com.example.internship.dto.product.ProductDto.Response.Full> getAll();
    void delete(Long id);
    void save(com.example.internship.dto.product.ProductDto.Response.Full productDto);
    List<com.example.internship.dto.product.ProductDto.Response.Full> getByName(String name);
    Optional<com.example.internship.dto.product.ProductDto.Response.Full> getById(Long id);
    ProductDto getProductById(Long id);

    // Продукт по id
    Optional<ProductDto> findById(Long id);
    // Поиск по условиям
    ProductSearchResult search(Optional<String> name, Optional<Long> categoryId, Optional<BigDecimal> priceFrom,
                               Optional<BigDecimal> priceTo, Integer pageSize, Integer pageNumber);
}
