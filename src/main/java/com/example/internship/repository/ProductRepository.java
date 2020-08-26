package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository <Product, Long>{

}