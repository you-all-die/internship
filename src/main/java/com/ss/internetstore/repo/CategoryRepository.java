package com.ss.internetstore.repo;

import com.ss.internetstore.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository <Category, Long> {

}
