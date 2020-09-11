package com.example.internship.helper;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public enum SpecificationHelper {
    ;

    /**
     * Комбинирует спецификации из списка в одну, объединяя с помощью оператора AND.
     *
     * @param specifications список спецификаций
     * @param <T> тип сущности
     * @return спецификация или null
     */
    public static <T> Specification<T> combineAll(final List<Specification<T>> specifications) {
        if (null == specifications || specifications.isEmpty()) {
            return null;
        }
        Specification<T> combinedSpecification = Specification.where(specifications.get(0));
        for (final Specification<T> specification : specifications.subList(1, specifications.size())) {
            combinedSpecification.and(specification);
        }
        return combinedSpecification;
    }

    /**
     * Возвращает паттерн, используемый в выражении LIKE.
     *
     * @param searchString искомая строка
     * @return паттерн
     */
    public static String getLikePattern(String searchString) {
        if (null == searchString || searchString.isEmpty()) {
            return "%";
        } else {
            return "%" + searchString.toLowerCase() + "%";
        }
    }
}
