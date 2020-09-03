package com.example.internship.mail;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderLineDto;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendOrderDetailsMessage(
            TestCustomerDto customer, TestOrderDto order)
            throws MessagingException;
}
