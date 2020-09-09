package com.example.internship.mail.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import com.example.internship.mail.service.impl.EmailServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class EmailServiceImplTest {
    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private final SpringTemplateEngine templateEngine = mock(SpringTemplateEngine.class);

    private EmailService emailService;

    CustomerDto customer;

    @BeforeEach
    public void before() {
        emailService = new EmailServiceImpl(javaMailSender, templateEngine);
    }

    @Test
    public void testSendRegistrationWelcomeMessageNullArg() {
        Throwable exception = assertThrows(MailServiceException.class, () -> emailService.sendRegistrationWelcomeMessage(null));
        assertEquals(exception.getMessage(), "Invalid customer");
    }
}
