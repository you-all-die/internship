package com.example.internship.service.category;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();
    Category findById(Long id);
    List<Category> findAllSortById();
    void removeCategory(Long id);
    void addCategory(Category category);
    List<Category> findByName(String name);
    CategorySearchResult search(Optional<String> name, Optional<Long> parentId, Integer pageSize, Integer pageNumber);
    void removeAll();
}
