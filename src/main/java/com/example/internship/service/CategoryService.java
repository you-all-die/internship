package com.example.internship.service;

import com.example.internship.dto.category.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto.Response.All> findAll();

    List<CategoryDto.Response.All> findSubcategories(Long parentId);

    List<CategoryDto.Response.IdAndName> findIdAndName();

    List<CategoryDto.Response.All> findByName(String name);

    Optional<CategoryDto.Response.All> findById(Long id);

    List<CategoryDto.Response.All> findTopLevelCategories();

    void delete(Long id);

    void save(CategoryDto.Request.All category);
}
