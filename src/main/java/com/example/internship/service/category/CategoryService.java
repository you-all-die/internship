package com.example.internship.service.category;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.dto.CategoryDto;
import com.example.internship.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    List<CategoryDto> findAllSortById();
    void removeCategory(Long id);
    void addCategory(CategoryDto category);
    void addCategory(Category category);
    List<CategoryDto> findByName(String name);
    CategorySearchResult search(Optional<String> name, Optional<Long> parentId, Integer pageSize, Integer pageNumber);
    void removeAll();
}
