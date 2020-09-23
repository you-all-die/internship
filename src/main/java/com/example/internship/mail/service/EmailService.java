package com.example.internship.mail.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.mail.exception.MailServiceException;

public interface EmailService {
    boolean sendOrderDetailsMessage(CustomerDto customer, OrderDto order) throws MailServiceException;

    //    boolean sendOrderDetailsMessage(CustomerDto customer, TestOrderDto order) throws MailServiceException;
    boolean sendRegistrationWelcomeMessage(CustomerDto customer) throws MailServiceException;
}
