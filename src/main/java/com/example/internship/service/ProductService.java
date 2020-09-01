package com.example.internship.service;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<ProductDto> findAll();

    void removeProduct(Long id);

    void addProduct(ProductDto productDto);

    void saveProduct(Product product);

    List<ProductDto> findByName(String name);

    Product findById(Long id);

    Long findMaxProduct();

    ProductDto getProductById(Long id);

}
