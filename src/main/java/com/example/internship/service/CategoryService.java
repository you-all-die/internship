package com.example.internship.service;

import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContainsIgnoreCase(name);
    }
}
