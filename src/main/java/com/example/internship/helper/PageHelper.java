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
    public static int calculateTotalPages(int total, int pageSize) {
        return total / pageSize + (total % pageSize > 0 ? 1 : 0);
    }
}
