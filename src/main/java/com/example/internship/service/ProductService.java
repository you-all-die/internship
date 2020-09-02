package com.example.internship.service;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;

import java.util.List;


public interface ProductService {

    List<ProductDto> findAll();

    void removeProduct(Long id);

    void addProduct(ProductDto productDto);

    void saveProduct(Product product);

    List<ProductDto> findByName(String name);

    Product findById(Long id);

    ProductDto getProductById(Long id);

}
