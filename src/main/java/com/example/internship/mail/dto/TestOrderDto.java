package com.example.internship.mail.dto;

import lombok.Data;

import java.util.List;

//TODO: нужно только для тестов, после того как появится оформление заказа, это ДТО нужно удалить
@Data
public class TestOrderDto {
    private final Long id;
    private final String date;
    private final List<TestOrderLineDto> orderLines;
    private final String comment;
    private final Long totalPrice;
}
