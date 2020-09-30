package com.example.internship.mail.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author Murashov A.A
 */
@Service("emailService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final static String ORDER_EMAIL_SUBJECT = "Новый заказ № ";
    private final static String REGISTRATION_EMAIL_SUBJECT = "Добро пожаловать!";

    private final JavaMailSender emailSender;
    private final ITemplateEngine thymeleafTemplateEngine;

    private final String noreplyEmail;

    private final String orderEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, ITemplateEngine thymeleafTemplateEngine,
                            @Value("${internship.email.noreply_email}") String noreplyEmail,
                            @Value("${internship.email.order_email}") String orderEmail) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.noreplyEmail = noreplyEmail;
        this.orderEmail = orderEmail;
    }

    private Future<Boolean> sendHtmlMessage(String to, String from, String subject, String htmlBody) throws MailServiceException {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            emailSender.send(message);
            log.info("Email sent!\n" + "Subject: " + subject + "\nTo: " +
                    to + "\nFrom: " + from + "\nMessage:\n" + htmlBody);
            return CompletableFuture.completedFuture(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Error sending e-mail to {}, exception {}", to, e.toString());
            throw new MailServiceException("Error sending e-mail " + subject + " to " + to + " from " + from, e);
        }
    }

    @Async()
    @Override
    public Future<Boolean> sendOrderDetailsMessage(CustomerDto customer, OrderDto order) throws MailServiceException {
        if (null == customer) {
            throw new MailServiceException("Invalid customer", new NullPointerException());
        }

        if (null == order) {
            throw new MailServiceException("Invalid order data", new NullPointerException());
        }

        if (null == order.getCustomerEmail()) {
            throw new MailServiceException("Invalid e-mail", new NullPointerException());
        }

        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("order", order);
        templateModel.put("totalPrice", order.getTotalPrice());
        templateModel.put("date", order.getDate());
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail/order", thymeleafContext);
        return sendHtmlMessage(order.getCustomerEmail(), orderEmail, ORDER_EMAIL_SUBJECT + order.getId(), htmlBody);
    }

    @Async()
    @Override
    public Future<Boolean> sendRegistrationWelcomeMessage(CustomerDto customer) throws MailServiceException {
        if (null == customer) {
            throw new MailServiceException("Invalid customer", new NullPointerException());
        }

        if (null == customer.getEmail()) {
            throw new MailServiceException("Invalid e-mail", new NullPointerException());
        }

        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customer", customer);
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail/welcome", thymeleafContext);
        return sendHtmlMessage(customer.getEmail(), noreplyEmail, REGISTRATION_EMAIL_SUBJECT, htmlBody);
    }
}
