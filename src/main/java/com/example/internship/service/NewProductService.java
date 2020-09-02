package com.example.internship.service;

import com.example.internship.dto.product.ProductDto;

import java.util.List;

public interface NewProductService {
    List<ProductDto.Response.Full> findAll();
    void delete(Long id);
    void save(ProductDto.Response.Full productDto);
    List<ProductDto.Response.Full> findByName(String name);
}
