package com.example.internship.helper;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class JoinHelper {

    /**
     * Объединяет строковые параметры, игнорируя значения, равные `null`.
     *
     * @param delimiter разделитель строк
     * @param words     объединяемые строки
     * @return строка
     */
    public static String join(CharSequence delimiter, String... words) {
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (StringUtils.isNotBlank(word)) {
                builder.append(" ").append(word);
            }
        }
        return builder.toString().trim();
    }
}