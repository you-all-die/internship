package com.example.internship.api;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import com.example.internship.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Murashov A.A
 */
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailRestController {
    private final EmailService emailService;
    private final CustomerService customerService;

    @GetMapping("/{id}/welcome")
    @Operation(summary = "Отправляет приветственное письмо пользователю")
    public ResponseEntity<String> sendWelcomeEmail(@PathVariable Long id) {
        Optional<CustomerDto> customer = customerService.getDtoById(id);
        if (customer.isPresent()) {
            if (null != customer.get().getEmail()) {
                try {
                    emailService.sendRegistrationWelcomeMessage(customer.get());
                    return new ResponseEntity<>("Email successfully sent!", HttpStatus.OK);
                } catch (MailServiceException exception) {
                    return new ResponseEntity<>("Failed to send email! " + exception.toString(),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("Failed to send email - anonymous user!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Failed to send email - user not found!", HttpStatus.NOT_FOUND);
        }
    }
}
