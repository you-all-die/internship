package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductPopularRepository<Product> {

    List<Product> findByNameContainsIgnoreCase(String name);
}
