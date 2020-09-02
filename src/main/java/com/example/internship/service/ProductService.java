package com.example.internship.service;

import com.example.internship.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<ProductDto> findAll();

    void removeProduct(Long id);

    void addProduct(ProductDto productDto);

    List<ProductDto> findByName(String name);
    // Продукт по id
    ProductDto findById(Long id);
    // Поиск по условиям
    List<ProductDto> search(Optional<String> name, Optional<Long> categoryId, Optional<BigDecimal> priceFrom,
                         Optional<BigDecimal> priceTo, Integer pageSize, Integer pageNumber);
}
