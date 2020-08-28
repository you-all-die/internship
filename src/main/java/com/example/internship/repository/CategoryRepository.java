package com.example.internship.repository;

import com.example.internship.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findByNameContainsIgnoreCase(String name);
}
