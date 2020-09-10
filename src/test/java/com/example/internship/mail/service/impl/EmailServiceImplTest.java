package com.example.internship.mail.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.dto.TestOrderDto;
import com.example.internship.mail.dto.TestOrderLineDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Murashov A.A
 */
public class EmailServiceImplTest {
    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private final ITemplateEngine templateEngine = mock(ITemplateEngine.class);

    private EmailService emailService;


    @BeforeEach
    public void before() {
        emailService = new EmailServiceImpl(javaMailSender, templateEngine, "a@a.com", "a@a.com");
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        when(javaMailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        when(templateEngine.process(anyString(), any(IContext.class))).thenReturn("");
    }

    /**
     * Проверка провального метода sendRegistrationWelcomeMessage:
     * <br> - Все аргументы null.
     */
    @Test
    public void testSendRegistrationWelcomeMessageNullArg() {
        assertThrows(MailServiceException.class, () -> emailService.sendRegistrationWelcomeMessage(null));
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    /**
     * Проверка провального метода sendRegistrationWelcomeMessage:
     * <br> - Отправка письма.
     */
    @Test
    public void testSendRegistrationWelcomeMessageAllArgs() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail("a@a.com");
        customer.setFirstName("name");
        try {
            assertTrue(emailService.sendRegistrationWelcomeMessage(customer));
        } catch (MailServiceException e) {
            Assert.fail(e.getMessage());
            verify(javaMailSender, never()).send(any(MimeMessage.class));
        }
    }

    /**
     * Проверка провального метода sendRegistrationWelcomeMessage:
     * <br> - Почта пользователя null.
     */
    @Test
    public void testSendRegistrationWelcomeMessageNullEmail() {
        CustomerDto customer = new CustomerDto();
        try {
            assertTrue(emailService.sendRegistrationWelcomeMessage(customer));
        } catch (MailServiceException e) {
            verify(javaMailSender, never()).send(any(MimeMessage.class));
        }
    }

    /**
     * Проверка провального метода sendOrderDetailsMessage:
     * <br> - Отправка письма.
     */
    @Test
    public void testSendOrderDetailsMessageAllArgs() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail("a@a.com");
        customer.setFirstName("name");
        TestOrderDto orderDto = new TestOrderDto(1L, "date",
                List.of(new TestOrderLineDto(1L, "name", 2L, 3L)), "comment", 600L);
        try {
            assertTrue(emailService.sendOrderDetailsMessage(customer, orderDto));
        } catch (MailServiceException e) {
            Assert.fail(e.getMessage());
            verify(javaMailSender, never()).send(any(MimeMessage.class));
        }
    }

    /**
     * Проверка провального метода sendOrderDetailsMessage:
     * <br> - Все аргументы null.
     */
    @Test
    public void testSendOrderDetailsMessageNullArgs() {
        assertThrows(MailServiceException.class, () -> emailService.sendOrderDetailsMessage(null, null));
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    /**
     * Проверка провального метода sendOrderDetailsMessage:
     * <br> - Покупатель null.
     */
    @Test
    public void testSendOrderDetailsMessageNullCustomer() {
        TestOrderDto orderDto = new TestOrderDto(1L, "date",
                List.of(new TestOrderLineDto(1L, "name", 2L, 3L)), "comment", 600L);
        assertThrows(MailServiceException.class, () -> emailService.sendOrderDetailsMessage(null, orderDto));
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    /**
     * Проверка провального метода sendOrderDetailsMessage:
     * <br> - Детали заказа null.
     */
    @Test
    public void testSendOrderDetailsMessageNullOrder() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail("a@a.com");
        customer.setFirstName("name");
        assertThrows(MailServiceException.class, () -> emailService.sendOrderDetailsMessage(customer, null));
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    /**
     * Проверка провального метода sendOrderDetailsMessage:
     * <br> - Почта пользователя null.
     */
    @Test
    public void testSendOrderDetailsMessageNullEmail() {
        CustomerDto customer = new CustomerDto();
        customer.setFirstName("name");
        assertThrows(MailServiceException.class, () -> emailService.sendOrderDetailsMessage(customer, null));
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }
}
