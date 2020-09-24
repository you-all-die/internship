package com.example.internship.specification;

import com.example.internship.entity.Category;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Aleksey Moiseychev
 */
@AllArgsConstructor
public class CategorySpecification implements Specification<Category> {

    private final String key;
    private final Object value;

    @Override
    public Predicate toPredicate(@NotNull Root<Category> root,
                                 @NotNull CriteriaQuery<?> criteriaQuery,
                                 @NotNull CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "name":
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + value.toString().toLowerCase() + "%");
            case "parentId":
                return criteriaBuilder.equal(root.get("parent").get("id"), value.toString());
            case "parentIdNull":
                return criteriaBuilder.isNull(root.get("parent").get("id"));
        }

        return null;
    }
}