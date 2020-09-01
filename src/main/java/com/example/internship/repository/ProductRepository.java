package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByNameContainsIgnoreCaseAndAndCategoryId(String name, Long categoryId);
}
