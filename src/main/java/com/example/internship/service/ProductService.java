package com.example.internship.service;

import com.example.internship.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;


public interface ProductService {

    List<ProductDto> findAll();

    void removeProduct(Long id);

    void addProduct(ProductDto productDto);

    List<ProductDto> findByName(String name);
    // Продукт по id
    ProductDto findById(Long id);
    // Продукт по id его категории
    List<ProductDto> findByCategoryId(Long categoryId);
    // Продукт по названию и id его категории
    List<ProductDto> findByNameAndAndCategoryId(String name, Long categoryId);
    // Возвращает продукты только с ценой ОТ и ДО
    List<ProductDto> filterByPrice(List<ProductDto> products, BigDecimal priceFrom, BigDecimal priceTo);
    // Возвращает продукты только с ценой ОТ
    List<ProductDto> filterByPrice(List<ProductDto> products, BigDecimal priceFrom);
}
