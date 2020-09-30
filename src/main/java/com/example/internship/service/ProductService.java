package com.example.internship.service;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.dto.ProductSearchResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<ProductDto> findAll();

    void removeProduct(Long id);

    void addProduct(ProductDto productDto);

    void saveProduct(Product product);

    List<ProductDto> findByName(String name);

    Product findByIdProduct(Long id);

    void removeAll();

    ProductDto getProductById(Long id);

    // Продукт по id
    Optional<ProductDto> findById(Long id);
    // Поиск по условиям
    ProductSearchResult search(String name, Long categoryId, BigDecimal priceFrom, BigDecimal priceTo, Integer pageSize,
                               Integer pageNumber);

}
