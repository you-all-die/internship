package com.example.internship.specification;

import com.example.internship.entity.Product;
import com.example.internship.helper.SpecificationHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GsProductSpecification {

    private static Specification<Product> withNameLike(final String searchString) {
        return (Specification<Product>) (product, query, builder) ->
                builder.like(builder.lower(product.get("name")), SpecificationHelper.getLikePattern(searchString));
    }

    private static Specification<Product> ofCategories(Collection<Long> categoryIds) {
        return (Specification<Product>) (product, query, builder) ->
                builder.in(product.get("category").get("id")).value(categoryIds);
    }

    private static Specification<Product> minimalAndMaximalPrices(BigDecimal minimalPrice, BigDecimal maximalPrice) {
        return (Specification<Product>) (product, query, builder) ->
                builder.and(
                        builder.greaterThanOrEqualTo(product.get("price").as(BigDecimal.class), minimalPrice),
                        builder.lessThanOrEqualTo(product.get("price").as(BigDecimal.class), maximalPrice)
                );
    }

    @NoArgsConstructor
    public static class Builder {
        private final List<Specification<Product>> specifications = new LinkedList<>();

        public Builder nameLike(String searchingString) {
            if (null != searchingString && !searchingString.isBlank()) {
                specifications.add(GsProductSpecification.withNameLike(searchingString));
            }
            return this;
        }

        public Builder ofCategories(List<Long> categoryIds) {
            if (null != categoryIds && !categoryIds.isEmpty()) {
                specifications.add(GsProductSpecification.ofCategories(categoryIds));
            }
            return this;
        }

        public Builder lowerUpperLimits(BigDecimal minimalPrice, BigDecimal maximalPrice) {
            if (null != minimalPrice && null != maximalPrice) {
                specifications.add(GsProductSpecification.minimalAndMaximalPrices(minimalPrice, maximalPrice));
            }
            return this;
        }

        public Specification<Product> build() {
            return SpecificationHelper.combineAll(specifications);
        }
    }
}
