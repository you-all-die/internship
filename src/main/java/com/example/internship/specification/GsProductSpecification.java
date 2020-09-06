package com.example.internship.specification;

import com.example.internship.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@RequiredArgsConstructor
public class GsProductSpecification {

    private final String key;
    private final Object value;

    public static Specification<Product> productWithNameLike(final String searchString) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + searchString + "%");
    }

    public static Specification<Product> productWithCategory(final Long categoryId) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.<Long>get("categoryId"), categoryId);
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
}
