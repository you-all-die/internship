package com.example.internship.service;

import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> findAll(){
        return (List<Product>) productRepository.findAll();
    }
}