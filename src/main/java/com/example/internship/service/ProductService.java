package com.example.internship.service;

import com.example.internship.dto.ProductSearchResult;
import com.example.internship.dto.product.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<ProductDto.Response.All> findAll();

    Optional<ProductDto.Response.All> findById(Long id);

    List<ProductDto.Response.All> findByName(String name);

    List<ProductDto.Response.All> findPopular(long limit);

    void delete(Long id);

    void save(ProductDto.Response.All productDto);

    // Поиск по условиям
    ProductSearchResult search(Optional<String> name, Optional<Long> categoryId, Optional<BigDecimal> priceFrom,
                               Optional<BigDecimal> priceTo, Integer pageSize, Integer pageNumber);
}
