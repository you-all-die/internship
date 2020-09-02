package com.example.internship.specification;

import com.example.internship.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Ivan Gubanov
 */
@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private String key;
    private Object value;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "priceFrom":
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value.toString());
            case "priceTo":
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value.toString());
            case "name":
                return criteriaBuilder.like(root.get("name"), "%" + value + "%");
            case "categoryId":
                return criteriaBuilder.equal(root.join("category").get("id"), value.toString());
        }
        return null;
    }
}
