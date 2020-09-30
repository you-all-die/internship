package com.example.internship.service.category;

import com.example.internship.dto.CategorySearchResult;
import com.example.internship.dto.CategoryDto;
import com.example.internship.entity.Category;
import com.example.internship.repository.projection.ParentCategoryProjection;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    List<CategoryDto> findAllSortById();
    void removeCategory(Long id);
    Category addCategory(CategoryDto category);
    List<CategoryDto> findByName(String name);
    CategorySearchResult search(String name, Long parentId, Integer pageSize, Integer pageNumber);
    void removeAll();
    List<CategoryDto> getParentCategoriesWithChildren();
}
