package com.example.internship.repository;


import com.example.internship.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Роман Каравашкин.
 */
@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long>,
        JpaSpecificationExecutor<ProductRating> {
    @Transactional
    @Query(value = "select avg(p.rating) from ProductRating p where p.productId=:productId")
    Double productRating(@Param("productId") Long productId);

}
