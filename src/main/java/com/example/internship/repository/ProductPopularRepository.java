package com.example.internship.repository;

import java.util.List;

public interface ProductPopularRepository<T> {
    List<T> findProductPopular(int limit);
}
