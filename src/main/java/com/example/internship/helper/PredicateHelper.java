package com.example.internship.helper;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public enum PredicateHelper {
    ;

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
