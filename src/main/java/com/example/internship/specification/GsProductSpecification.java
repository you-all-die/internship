package com.example.internship.specification;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum GsProductSpecification {
    ;

    public static Specification<Product> productWithNameLike(final String searchString) {
        return (Specification<Product>) (product, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(product.get("name"), "%" + searchString + "%");
    }

    public static Specification<Product> productWithCategory(final Long categoryId) {
        return (Specification<Product>) (product, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(product.<Long>get("categoryId"), categoryId);
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
