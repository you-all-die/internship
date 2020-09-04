package com.example.internship.mail.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestOrderDto {
    private final Long id;
    private final String date;
    private final List<TestOrderLineDto> orderLines;
    private final String comment;
    private final Long totalPrice;
}
