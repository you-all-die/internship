package com.example.internship.specification;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum GsProductSpecification {
    ;

    public static Specification<Product> withNameLike(final String searchString) {
        return (Specification<Product>) (product, query, builder) ->
                builder.like(builder.lower(product.get("name")), getLikePattern(searchString));
    }

    public static Specification<Product> ofCategories(final Collection<Long> categoryIds) {
        return (Specification<Product>) (product, query, builder) ->
                builder.in(product.<Long>get("categoryId").in(categoryIds));
    }

    /*
        public static Specification<Product> productPriceGreaterOrEqual(final BigDecimal minimum) {
            return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
                BigDecimal price = (BigDecimal) root.get("price");
                int compareTo = price.compareTo(minimum);
                return criteriaBuilder.<Integer>greaterThanOrEqualTo(compareTo, 0);
            };
        }

        public static Specification<Product> productPriceLowerOrEqual(final BigDecimal maximum) {
            return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.le(root.get("price"), maximum);
        }
    */
    private static String getLikePattern(String searchString) {
        if (null == searchString || searchString.isEmpty()) {
            return "%";
        } else {
            return "%" + searchString.toLowerCase() + "%";
        }
    }
}
