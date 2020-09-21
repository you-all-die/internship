package com.example.internship.mail.service;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.dto.TestOrderDto;
import com.example.internship.mail.exception.MailServiceException;

public interface EmailService {
    boolean sendOrderDetailsMessage(CustomerDto customer, TestOrderDto order) throws MailServiceException;
    boolean sendRegistrationWelcomeMessage(CustomerDto customer) throws MailServiceException;
}
