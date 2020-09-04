package com.example.internship.mail;

import lombok.Data;

@Data
public class TestOrderLineDto {
    private final Long id;
    private final String productName;
    private final Long productQuantity;
    private final Long price;
}
