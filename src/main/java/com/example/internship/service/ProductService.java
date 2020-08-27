package com.example.internship.service;

import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContainsIgnoreCase(name);
    }

    public void hideProduct(long id) {

    }

}
