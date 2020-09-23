package com.example.adminapplication.service;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchResult;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
public interface CategoryService {

    CategoryDto findById(Long id);

    List<CategoryDto> findAllSortById();

    void removeCategory(Long id);

    void addCategory(CategoryDto category);

    CategorySearchResult searchResult(String name, Long parentId, Integer pageSize, Integer pageNumber);

    List<CategoryDto> getParentCategoriesWithChildren();

}
