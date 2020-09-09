package com.example.adminapplication.service;

import com.example.adminapplication.dto.CategoryDto;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
public interface CategoryService {

    List<CategoryDto> findAll();

    CategoryDto findById(Long id);

    List<CategoryDto> findAllSortById();

    void removeCategory(Long id);

    void addCategory(CategoryDto category);

    List<CategoryDto> findByName(String name);

}
