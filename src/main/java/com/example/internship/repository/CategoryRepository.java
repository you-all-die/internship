package com.example.internship.repository;

import com.example.internship.entity.Category;
import com.example.internship.repository.projection.ParentCategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    List<Category> findByNameContainsIgnoreCase(String name);
    List<Category> findAllByOrderByIdAscParentIdAsc();
    List<Category> findAllByParentNull();


    @Query(value = "SELECT DISTINCT a.parent_id as id, b.name FROM Category a " +
            "INNER JOIN Category b on a.parent_id = b.id " +
            "ORDER BY a.parent_id", nativeQuery = true)
    List<ParentCategoryProjection> getParentCategory();
}

