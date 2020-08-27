package com.example.internship.repository;


import com.example.internship.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductPepositoryImpl implements ProductRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findProductLimit(int limit) {
        return entityManager.createQuery("SELECT product FROM Product product",
                Product.class).setMaxResults(limit).getResultList();
    }
}


