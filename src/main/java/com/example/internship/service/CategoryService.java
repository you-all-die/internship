package com.example.internship.service;

import com.example.internship.dto.category.CategoryDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto.Response.Full> findAll();
    List<CategoryDto.Response.Full> findSubcategories(Long parentId);
    List<CategoryDto.Response.IdAndName> getIdAndName();
    Optional<CategoryDto.Response.Full> findById(Long id) throws EntityNotFoundException;
    void delete(Long id);
    void save(CategoryDto.Request.Full category);
    List<CategoryDto.Response.Full> findByName(String name);
}
