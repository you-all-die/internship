package com.example.internship.specification;

import com.example.internship.entity.Product;
import com.example.internship.helper.SpecificationHelper;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum GsProductSpecification {
    ;

    public static Specification<Product> withNameLike(final String searchString) {
        return (Specification<Product>) (product, query, builder) ->
                builder.like(builder.lower(product.get("name")), SpecificationHelper.getLikePattern(searchString));
    }

    public static Specification<Product> ofCategories(Collection<Long> categoryIds) {
        return (Specification<Product>) (product, query, builder) ->
                builder.in(product.get("category").get("id")).value(categoryIds);
    }

}
