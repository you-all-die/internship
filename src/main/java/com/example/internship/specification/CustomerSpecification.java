package com.example.internship.specification;

import com.example.internship.entity.Customer;
import com.example.internship.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/*
@author Romodin Aleksey
 */
@AllArgsConstructor
public class CustomerSpecification implements Specification<Customer> {
    private String key;
    private Object value;

    //Параметры для составления запроса
    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "firstName":
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        "%" + value.toString().toLowerCase() + "%");
            case "middleName":
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")),
                        "%" + value.toString().toLowerCase() + "%");
            case "lastName":
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                        "%" + value.toString().toLowerCase() + "%");
            case "email":
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + value.toString().toLowerCase() + "%");
        }
        return null;
    }
}
