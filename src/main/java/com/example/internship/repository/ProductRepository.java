package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByNameContainsIgnoreCase(String name);

    /**
     * @author Самохвалов Юрий Алексеевич
     */
    List<Product> findAllByCategoryId(long categoryId);

    /**
     * @author Самохвалов Юрий Алексеевич
     */
//    @Query("select p.id from Product p where p.category_id = :categoryId")
    List<Long> findIdByCategoryId(long categoryId);
}
