package com.example.internship.repository;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    List<Category> findByNameContainsIgnoreCase(String name);
    List<Category> findAllByOrderByIdAscParentIdAsc();
    List<Category> findByParentId(Long parentId);

    @Query("SELECT c.id, c.name FROM Category c")
    List<CategoryDto.Response.IdAndName> getIdAndName();
}

