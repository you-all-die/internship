package com.example.internship.service.category;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public interface GsCategoryService {
    List<CategoryDto.Response.AllWithSubcategories> findTopCategories();
    List<CategoryDto.Response.All> findAncestors(Long categoryId);
    List<Long> findDescendants(Long categoryId);
    Optional<Category> findById(long id);
    Category save(CategoryDto.Request.All category);
    void deleteAll();
}
