package com.example.internship.mail.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.mail.exception.MailServiceException;

import java.util.concurrent.Future;

public interface EmailService {
    Future<Boolean> sendOrderDetailsMessage(CustomerDto customer, OrderDto order) throws MailServiceException;
    Future<Boolean> sendRegistrationWelcomeMessage(CustomerDto customer) throws MailServiceException;
}
