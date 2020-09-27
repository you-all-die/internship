package com.example.internship.specification;

import com.example.internship.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@AllArgsConstructor
public class FeedbackSpecification implements Specification<Feedback> {

    private final String key;
    private final Object value;

    @Override
    public Predicate toPredicate(@NonNull Root<Feedback> root,
                                 @NonNull CriteriaQuery<?> criteriaQuery,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "productId":
                return criteriaBuilder.equal(root.get("productId"), value.toString());
            case "authorId":
                return criteriaBuilder.equal(root.get("authorId"), value.toString());
            default: throw new RuntimeException("unreachable");
        }
    }
}
