package com.example.internship.specification;

import com.example.internship.entity.Product;
import com.example.internship.helper.PredicateHelper;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public class GsProductSpecification implements Specification<Product> {

    private final String searchString;
    private final Collection<Long> categoryIds;
    private final BigDecimal lowerLimit;
    private final BigDecimal upperLimit;

    private GsProductSpecification(
            Builder builder
    ) {
        this.searchString = builder.searchString;
        this.categoryIds = builder.categoryIds;
        this.lowerLimit = builder.lowerLimit;
        this.upperLimit = builder.upperLimit;
    }

    @Override
    public Predicate toPredicate(
            @NotNull Root<Product> product,
            @NotNull CriteriaQuery<?> query,
            @NotNull CriteriaBuilder builder
    ) {
        final List<Predicate> predicates = new LinkedList<>();

        if (null != searchString && !searchString.isEmpty()) {
            predicates.add(builder.like(builder.lower(product.get("name")), PredicateHelper.getLikePattern(searchString)));
        }

        if (null != categoryIds && !categoryIds.isEmpty()) {
            predicates.add(builder.in(product.get("category").get("id")).value(categoryIds));
        }

        if (null != lowerLimit && null != upperLimit) {
            predicates.add(builder.between(product.get("price").as(BigDecimal.class), lowerLimit, upperLimit));
        } else if (null != lowerLimit) {
            predicates.add(builder.greaterThanOrEqualTo(product.get("price"), lowerLimit));
        } else {
            predicates.add(builder.lessThanOrEqualTo(product.get("price").as(BigDecimal.class), upperLimit));
        }

        return builder.and(predicates.toArray(new Predicate[]{}));
    }

    @NoArgsConstructor
    public static class Builder {
        private String searchString;
        private Collection<Long> categoryIds;
        private BigDecimal lowerLimit;
        private BigDecimal upperLimit;

        public Builder nameLike(String searchingString) {
            this.searchString = searchingString;
            return this;
        }

        public Builder ofCategories(List<Long> categoryIds) {
            this.categoryIds = categoryIds;
            return this;
        }

        public Builder priceBetween(BigDecimal lowerLimit, BigDecimal upperLimit) {
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
            return this;
        }

        public Specification<Product> build() {
            return new GsProductSpecification(this);
        }
    }
}
