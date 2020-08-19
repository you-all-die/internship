package com.gustav474.internship.model.repository;

import com.gustav474.internship.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Long> {

}
