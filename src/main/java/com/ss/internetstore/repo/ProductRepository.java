package com.ss.internetstore.repo;

import com.ss.internetstore.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository <Product, Long>{

}
