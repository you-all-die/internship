package com.example.internship.repository;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    List<Long> findIdByCategoryId(long categoryId);

    /**
     * @author Самохвалов Юрий Алексеевич
     */
    @Query("select min(price) from Product")
    Optional<BigDecimal> findMinimalPrice();

    /**
     * @author Самохвалов Юрий Алексеевич
     */
    @Query("select max(price) from Product")
    Optional<BigDecimal> findMaximalPrice();


}
