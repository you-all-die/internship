package com.example.internship.helper;

public enum PageHelper {
    ;

    public static int calculate(int total, int pageSize) {
        return total / pageSize + (total % pageSize > 0 ? 1 : 0);
    }
}
