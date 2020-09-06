package com.example.internship.specification;

import com.example.internship.entity.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public class GsProductSpecification {

    public static Specification<Product> productWithCategory(final Long categoryId) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(
                    @NotNull Root<Product> root,
                    @NotNull CriteriaQuery<?> criteriaQuery,
                    @NotNull CriteriaBuilder criteriaBuilder
            ) {
                return null;
            }
        };
    }
}
