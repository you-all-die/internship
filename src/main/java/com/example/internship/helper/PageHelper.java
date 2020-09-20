package com.example.internship.helper;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum PageHelper {
    ;

    /**
     * Возвратить количество страниц, нужное для отображения элементов
     *
     * @param total    общее количество элементов
     * @param pageSize количество элементов на странице
     * @return количество страниц
     */
    public static int calculateTotalPages(long total, int pageSize) {
        return (int) total / pageSize + ((int) total % pageSize > 0 ? 1 : 0);
    }
}
