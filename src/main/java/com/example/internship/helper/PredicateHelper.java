package com.example.internship.helper;

import lombok.experimental.UtilityClass;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@UtilityClass
public class PredicateHelper {

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
