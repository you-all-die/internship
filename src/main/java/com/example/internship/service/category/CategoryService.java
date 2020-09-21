package com.example.internship.service.category;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto.Response.AllWithParentIdParentName> findAll();
    CategoryDto.Response.AllWithParentIdParentName findById(Long id);
    List<CategoryDto.Response.AllWithParentIdParentName> findAllSortById();
    void removeCategory(Long id);
    void addCategory(CategoryDto.Request.All category);
    void addCategory(Category category);
    List<CategoryDto.Response.AllWithParentIdParentName> findByName(String name);
    CategorySearchResult search(Optional<String> name, Optional<Long> parentId, Integer pageSize, Integer pageNumber);
    void removeAll();
}
