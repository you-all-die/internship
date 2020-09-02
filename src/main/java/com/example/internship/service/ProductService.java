package com.example.internship.service;

import com.example.internship.dto.ProductDto;

import java.util.List;


public interface ProductService {
    List<ProductDto> findAll();
    void removeProduct(Long id);
    void addProduct(ProductDto productDto);
    List<ProductDto> findByName(String name);

    List<com.example.internship.dto.product.ProductDto.Response.Full> findAll();
    void delete(Long id);
    void save(com.example.internship.dto.product.ProductDto.Response.Full productDto);
    List<com.example.internship.dto.product.ProductDto.Response.Full> findByName(String name);
}
