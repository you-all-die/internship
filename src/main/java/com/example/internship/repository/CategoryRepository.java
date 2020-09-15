package com.example.internship.repository;

import com.example.internship.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    List<Category> findByNameContainsIgnoreCase(String name);
    List<Category> findAllByOrderByIdAscParentIdAsc();
    List<Category> findAllByParentNull();
}

