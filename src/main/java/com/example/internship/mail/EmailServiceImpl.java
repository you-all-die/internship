package com.example.internship.mail;


import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service("emailService")
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String NOREPLY_ADDRESS = "noreply@interstellarshop.com";

    private JavaMailSender emailSender;

    private SpringTemplateEngine thymeleafTemplateEngine;

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);

    }

    @Override
    public void sendOrderDetailsMessage(TestCustomerDto customer, TestOrderDto order)
            throws MessagingException {
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("user", customer);
        templateModel.put("order", order);
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

        sendHtmlMessage(customer.getEmail(), "subject", htmlBody);
    }
}
