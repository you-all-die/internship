package com.example.internship.mail.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {
    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private final SpringTemplateEngine templateEngine = mock(SpringTemplateEngine.class);

    private EmailService emailService;

    CustomerDto customer;

    @BeforeEach
    public void before() {
        emailService = new EmailServiceImpl(javaMailSender, templateEngine);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        when(templateEngine.process(anyString(), any(IContext.class))).thenReturn("");
    }

    @Test
    public void testSendRegistrationWelcomeMessageNullArg() {
        Throwable exception = assertThrows(MailServiceException.class, () -> emailService.sendRegistrationWelcomeMessage(null));
        assertEquals("Invalid customer", exception.getMessage());
    }

    @Test
    public void testSendRegistrationWelcomeMessageAllArgs() {
        customer = new CustomerDto();
        customer.setEmail("a@a.com");
        customer.setFirstName("name");
        try {
            assertTrue(emailService.sendRegistrationWelcomeMessage(customer));
        } catch (MailServiceException e) {
            Assert.fail(e.getMessage());
        }
    }
}
