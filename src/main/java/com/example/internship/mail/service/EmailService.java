package com.example.internship.mail.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.dto.TestOrderDto;

public interface EmailService {
    void sendOrderDetailsMessage(CustomerDto customer, TestOrderDto order);
    void sendRegistrationWelcomeMessage(CustomerDto customer);
}
