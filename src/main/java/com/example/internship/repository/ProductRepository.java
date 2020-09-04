package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByNameContainsIgnoreCase(String name);

    @Query("select * from Product limit :limit")
    List<Product> findPopular(@Param("limit") long limit);
}
