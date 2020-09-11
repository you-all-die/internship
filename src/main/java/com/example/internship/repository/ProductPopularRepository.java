package com.example.internship.repository;

import java.util.List;

//TODO: перенести реализацию в ProductService
public interface ProductPopularRepository<T> {
    List<T> findProductPopular(int limit);
}
