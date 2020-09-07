package com.example.internship.service;

import com.example.internship.dto.category.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface GsCategoryService {
    List<CategoryDto.Response.AllWithParentAndSubcategories> findAll();
    Optional<CategoryDto.Response.All> findById(long id);
    void save(CategoryDto.Request.All categoryDto);
    void delete(long id);
}
