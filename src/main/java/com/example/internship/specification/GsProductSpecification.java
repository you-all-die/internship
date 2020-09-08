package com.example.internship.specification;

import com.example.internship.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum GsProductSpecification {
    ;

    public static Specification<Product> productWithNameLike(final String searchString) {
        return (Specification<Product>) (product, query, builder) ->
//                builder.like(builder.lower(product.get("name")), getLikePattern(searchString));
        builder.isNotNull(product.get("name"));
    }

    public static Specification<Product> productOfCategories(final List<Long> ids) {
        return (Specification<Product>) (product, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.in(product.get("categoryId").in(ids));
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
