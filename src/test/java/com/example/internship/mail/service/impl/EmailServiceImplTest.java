package com.example.internship.mail.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceImplTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private EmailService emailService;

    @Before
    public void before() {
        when(javaMailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
    }

    @Test
    public void testSendRegistrationWelcomeMessageNullArg() {
        Throwable exception = assertThrows(MailServiceException.class, () -> emailService.sendRegistrationWelcomeMessage(null));
        assertEquals("Invalid customer", exception.getMessage());
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    public void testSendRegistrationWelcomeMessageAllArgs() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail("a@a.com");
        customer.setFirstName("name");
        try {
            assertTrue(emailService.sendRegistrationWelcomeMessage(customer));
        } catch (MailServiceException e) {
            Assert.fail(e.getMessage());
        }
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}
