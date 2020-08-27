package com.example.internship.repository;

import com.example.internship.entity.Product;
import java.util.List;

public interface ProductRepository{
    List<Product> findProductLimit(int limit);
}