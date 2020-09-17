package com.example.internship.specification.customer;

import com.example.internship.entity.Customer;
import com.example.internship.entity.Customer_;
import com.example.internship.helper.PredicateHelper;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Самохвалов Юрий Алексеевич
 * 
 * Спецификация поиска покупателей, позволяющая отбирать покупателей по содержанию
 * строки поиска в именах, телефоне и почте
 */
@Builder
public class CustomerSpecificator implements Specification<Customer> {
    
    private final String searchString;
    
    @Override
    public Predicate toPredicate(
            @NonNull Root<Customer> root, 
            @NonNull CriteriaQuery<?> cr, 
            @NonNull CriteriaBuilder cb
    ) {
        if (StringUtils.isBlank(searchString)) {
            return null;
        }
        String likePattern = PredicateHelper.getLikePattern(searchString);
        return cb.or(
                cb.like(cb.lower(root.get(Customer_.firstName)), likePattern),
                cb.like(cb.lower(root.get(Customer_.middleName)), likePattern),
                cb.like(cb.lower(root.get(Customer_.lastName)), likePattern),
                cb.like(cb.lower(root.get(Customer_.phone)), likePattern),
                cb.like(cb.lower(root.get(Customer_.email)), likePattern)
        );
    }
}
