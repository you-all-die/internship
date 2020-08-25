package com.example.internship.repo;

import com.example.internship.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository <Product, Long>{

}
