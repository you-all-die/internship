package com.example.internship.mail;

import lombok.Data;

import java.util.List;

@Data
public class TestOrderDto {
    private final Long id;
    private final String date;
    private final List<TestOrderLineDto> orderLines;
}
