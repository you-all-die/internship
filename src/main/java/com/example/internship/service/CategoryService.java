package com.example.internship.service;

import com.example.internship.entity.Category;
import com.example.internship.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(new Category());
    }

    public List<Category> findAllSortById() {
        return categoryRepository.findAllByOrderByIdAscParentIdAsc();
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
