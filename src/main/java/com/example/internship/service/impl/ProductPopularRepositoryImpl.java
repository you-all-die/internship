package com.example.internship.service.impl;

import com.example.internship.entity.Product;
import com.example.internship.repository.ProductPopularRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductPopularRepositoryImpl implements ProductPopularRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findProductPopular(int limit) {
        return entityManager.createQuery("SELECT product FROM Product product",
                Product.class).setMaxResults(limit).getResultList();
    }
}

