package com.example.internship.specification;

import com.example.internship.entity.Category;
import com.example.internship.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Aleksey Moiseychev
 */
@AllArgsConstructor
public class CategorySpecification implements Specification<Category> {

    private String key;
    private Object value;

    /**
     * TODO: есть предложение, в этом и других подобных классах,
     * что бы не писать new CategorySpecification("name", name.orElse(""))) добавить соответствующие статические методы:
     * CategorySpecification.name(name.orElse(""))
     * CategorySpecification.parent(parentId)
     *
     * public static CategorySpecification name(String name) {
     *     return new CategorySpecification("name", name);
     * }
     *
     * это позволит избежать ошибок при написании ключей типа "name", "parentId" и т.д.
     * */
    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "name":
                return criteriaBuilder.like(root.get("name"), "%" + value + "%");
            case "parentId":
                return criteriaBuilder.equal(root.join("parent").get("id"), value.toString());
        }
        return null;
    }
}
