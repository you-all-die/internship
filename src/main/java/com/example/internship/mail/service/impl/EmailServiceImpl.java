package com.example.internship.mail.service.impl;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.dto.TestOrderDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service("emailService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final static String ORDER_EMAIL_SUBJECT = "Новый заказ № ";
    private final static String REGISTRATION_EMAIL_SUBJECT = "Добро пожаловать!";

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${internship.email.noreply_email}")
    private String noreplyEmail;

    @Value("${internship.email.order_email}")
    private String orderEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    private boolean sendHtmlMessage(String to, String from, String subject, String htmlBody) throws MailServiceException {

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
            return true;
        } catch (Exception e) {
            log.error("Error sending e-mail to {}, exception {}", to, e.toString());
            throw new MailServiceException("Error sending e-mail " + subject + " to " + to + " from " + from, e);
        }
    }

    @Override
    public boolean sendOrderDetailsMessage(CustomerDto customer, TestOrderDto order)  throws MailServiceException {
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customer", customer);
        templateModel.put("order", order);
        templateModel.put("orderLines", order.getOrderLines());
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail/order", thymeleafContext);
        return sendHtmlMessage(customer.getEmail(), orderEmail, ORDER_EMAIL_SUBJECT + order.getId(), htmlBody);
    }

    @Override
    public boolean sendRegistrationWelcomeMessage(CustomerDto customer) throws MailServiceException {
        if (null == customer) {
            throw new MailServiceException("Invalid customer", new NullPointerException());
        }
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customer", customer);
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail/welcome", thymeleafContext);
        return sendHtmlMessage(customer.getEmail(), noreplyEmail, REGISTRATION_EMAIL_SUBJECT, htmlBody);
    }
}
