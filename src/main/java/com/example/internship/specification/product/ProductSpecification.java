package com.example.internship.specification.product;

import com.example.internship.entity.Category_;
import com.example.internship.entity.Product;
import com.example.internship.entity.Product_;
import com.example.internship.helper.PredicateHelper;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public class ProductSpecification implements Specification<Product> {

    private final String searchString;
    private final Collection<Long> categoryIds;
    private final BigDecimal lowerLimit;
    private final BigDecimal upperLimit;

    private ProductSpecification(
            Builder builder
    ) {
        this.searchString = builder.searchString;
        this.categoryIds = builder.categoryIds;
        this.lowerLimit = builder.lowerLimit;
        this.upperLimit = builder.upperLimit;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<Product> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder builder
    ) {
        final List<Predicate> predicates = new ArrayList<>();

        if (null != searchString && !searchString.isEmpty()) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get(Product_.name)), PredicateHelper.getLikePattern(searchString)),
                    builder.like(builder.lower(root.get(Product_.description)), PredicateHelper.getLikePattern(searchString))
            ));
        }

        if (null != categoryIds) {
            predicates.add(builder.in(root.get(Product_.category).get(Category_.ID)).value(categoryIds));
        }

        if (null != lowerLimit && null != upperLimit) {
            predicates.add(builder.between(root.get(Product_.price).as(BigDecimal.class), lowerLimit, upperLimit));
        } else if (null != lowerLimit) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Product_.price), lowerLimit));
        } else {
            predicates.add(builder.lessThanOrEqualTo(root.get(Product_.price).as(BigDecimal.class), upperLimit));
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
            return new ProductSpecification(this);
        }
    }
}
