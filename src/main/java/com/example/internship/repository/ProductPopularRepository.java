package com.example.internship.repository;

import com.example.internship.entity.Product;

import java.util.List;

public interface ProductPopularRepository<T> {
    List<T> findProductPopular(int limit);
}