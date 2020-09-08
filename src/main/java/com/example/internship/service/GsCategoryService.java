package com.example.internship.service;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;

import java.util.List;
import java.util.Optional;

public interface GsCategoryService {
    List<CategoryDto.Response.AllWithParentSubcategories> findAll();
    List<CategoryDto.Response.AllWithParentSubcategories> findTopCategories();
    List<Long> findAncestors(Category category);
    List<Long> findDescendants(Category category);
    List<Long> findDescendants(Long categoryId);
    Optional<Category> findById(long id);
    void save(CategoryDto.Request.All categoryDto);
    void delete(long id);
}
