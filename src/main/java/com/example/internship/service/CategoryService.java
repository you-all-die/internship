package com.example.internship.service;

import com.example.internship.dto.category.CategoryDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CategoryService {
    List<CategoryDto.Response.Full> findAll();
    List<CategoryDto.Response.Full> findSubcategories(Long parentId);
    List<CategoryDto.Response.IdAndName> getIdAndName();
    CategoryDto.Response.Full findById(Long id) throws EntityNotFoundException;
    void removeCategory(Long id);
    void addCategory(CategoryDto.Request.Full category);
    List<CategoryDto.Response.Full> findByName(String name);
}
