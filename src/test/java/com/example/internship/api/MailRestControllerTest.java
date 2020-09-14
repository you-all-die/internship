package com.example.internship.api;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.service.EmailService;
import com.example.internship.mail.service.impl.EmailServiceImpl;
import com.example.internship.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.IContext;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Murashov A.A
 */
public class MailRestControllerTest {

    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private final ITemplateEngine templateEngine = mock(ITemplateEngine.class);

    private final CustomerService customerService = mock(CustomerService.class);

    private MailRestController mailRestController;

    private final Long CUSTOMER_ID1 = 1L;
    private final Long CUSTOMER_ID2 = 2L;
    private final Long CUSTOMER_ID3 = 3L;
    private final Long CUSTOMER_ID4 = 4L;

    @Before
    public void before() {
        CustomerDto customer1 = new CustomerDto();
        customer1.setId(CUSTOMER_ID1);
        customer1.setEmail("a@a.com");

        CustomerDto customer3 = new CustomerDto();
        customer3.setId(CUSTOMER_ID3);

        CustomerDto customer4 = new CustomerDto();
        customer4.setId(CUSTOMER_ID4);
        customer4.setEmail("");

        EmailService emailService = new EmailServiceImpl(javaMailSender, templateEngine, "a@a.com", "a@a.com");
        mailRestController = new MailRestController(emailService, customerService);
        when(customerService.getDtoById(eq(CUSTOMER_ID1))).thenReturn(Optional.of(customer1));
        when(customerService.getDtoById(eq(CUSTOMER_ID2))).thenReturn(Optional.empty());
        when(customerService.getDtoById(eq(CUSTOMER_ID3))).thenReturn(Optional.of(customer3));
        when(customerService.getDtoById(eq(CUSTOMER_ID4))).thenReturn(Optional.of(customer4));
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        when(javaMailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        when(templateEngine.process(anyString(), any(IContext.class))).thenReturn("");
    }

    /**
     * Проверка провального метода sendWelcomeEmail:
     * <br> - Отправка письма.
     */
    @Test
    public void shouldSendEmail() {
        ResponseEntity<String> response = mailRestController.sendWelcomeEmail(CUSTOMER_ID1);
        assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Проверка провального метода sendWelcomeEmail:
     * <br> - Пользователь null.
     */
    @Test
    public void errorNotFoundEmail() {
        ResponseEntity<String> response = mailRestController.sendWelcomeEmail(CUSTOMER_ID2);
        assertSame(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * Проверка провального метода sendWelcomeEmail:
     * <br> - Почта null.
     */
    @Test
    public void errorNullUserEmail() {
        ResponseEntity<String> response = mailRestController.sendWelcomeEmail(CUSTOMER_ID3);
        assertSame(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Проверка провального метода sendWelcomeEmail:
     * <br> - Неверная почта пользователя.
     */
    @Test
    public void errorInvalidUserData() {
        ResponseEntity<String> response = mailRestController.sendWelcomeEmail(CUSTOMER_ID4);
        assertSame(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
