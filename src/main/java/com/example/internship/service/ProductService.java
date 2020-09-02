package com.example.internship.service;

import com.example.internship.dto.product.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    @Deprecated
    List<com.example.internship.dto.ProductDto> findAll();

    @Deprecated
    void removeProduct(Long id);

    @Deprecated
    void addProduct(com.example.internship.dto.ProductDto productDto);

    @Deprecated
    List<com.example.internship.dto.ProductDto> findByName(String name);

    List<ProductDto.Response.Full> getAll();

    Optional<ProductDto.Response.Full> getById(Long id);

    void delete(Long id);

    void save(ProductDto.Response.Full productDto);

    List<ProductDto.Response.Full> getByName(String name);

    com.example.internship.dto.ProductDto getProductById(Long id);

    // Продукт по id
    @Deprecated
    Optional<com.example.internship.dto.ProductDto> findById(Long id);

    // Поиск по условиям
    com.example.internship.dto.ProductSearchResult search(Optional<String> name, Optional<Long> categoryId, Optional<BigDecimal> priceFrom,
                               Optional<BigDecimal> priceTo, Integer pageSize, Integer pageNumber);
}
