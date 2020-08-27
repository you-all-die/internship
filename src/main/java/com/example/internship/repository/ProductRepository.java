package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByNameContainsIgnoreCase(String name);
}
