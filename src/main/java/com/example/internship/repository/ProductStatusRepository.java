package com.example.internship.repository;

import com.example.internship.entity.ProductStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductStatusRepository extends CrudRepository<ProductStatus, Long> {
    List<ProductStatus> findAll(Sort id);
}
