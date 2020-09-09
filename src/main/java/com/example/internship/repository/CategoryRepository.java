package com.example.internship.repository;

import com.example.internship.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    List<Category> findByNameContainsIgnoreCase(String name);
    List<Category> findAllByOrderByIdAscParentIdAsc();
}

