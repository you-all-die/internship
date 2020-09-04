package com.example.internship.mail.service;

import com.example.internship.mail.dto.TestCustomerDto;
import com.example.internship.mail.dto.TestOrderDto;

public interface EmailService {
    void sendOrderDetailsMessage(TestCustomerDto customer, TestOrderDto order);
    void sendRegistrationWelcomeMessage(TestCustomerDto customer);
}
