package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public interface GsCategoryService {
    List<CategoryDto.Response.AllWithParentSubcategories> findAll();
    List<CategoryDto.Response.AllWithParentSubcategories> findTopCategories();
    List<CategoryDto.Response.All> findAncestors(Category category);
    List<CategoryDto.Response.All> findAncestors(Long categoryId);
    List<Long> findDescendants(Category category);
    List<Long> findDescendants(Long categoryId);
    Optional<Category> findById(long id);
    void save(Category category);
    Category save(CategoryDto.Response.All categoryDto);
    void delete(long id);
    void deleteAll();
}
