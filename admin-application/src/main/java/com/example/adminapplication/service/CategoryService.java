package com.example.adminapplication.service;

import com.example.adminapplication.dto.CategoryDto;
import com.example.adminapplication.dto.CategorySearchResult;
import com.example.adminapplication.dto.CategorySearchRequest;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
public interface CategoryService {

    CategoryDto findById(Long id);

    List<CategoryDto> findAll();

    void removeCategory(Long id);

    void addCategory(CategoryDto category);

    CategorySearchResult searchResult(CategorySearchRequest request);

    List<CategoryDto> getParentCategoriesWithChildren();

}
