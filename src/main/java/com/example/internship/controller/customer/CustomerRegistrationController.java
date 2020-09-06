package com.example.internship.controller.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.mail.service.EmailService;
import com.example.internship.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class CustomerRegistrationController {

    private final CustomerService customerService;

    private final EmailService emailService;

    @GetMapping
    public String registration(CustomerDto customerDto) {

        return "customer/registration";
    }

    // Регистрация нового покупателя
    @PostMapping
    public String newCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult, Model model,
                              @RequestParam Map<String,String> allParams) {
        // Если неверно заполнены поля
        if (bindingResult.hasErrors()) {
            return "customer/registration";
        }
        // Если пароли не совпадают
        if (!allParams.get("password").equals(allParams.get("passwordConfirm"))) {
            model.addAttribute("errorMessage", "Пароли не совпадают.");
            return "customer/registration";
        }

        // регистрация покупателя и редирект на страницу его профиля и отправка письма на почту
        emailService.sendRegistrationWelcomeMessage(customerDto);
        return "redirect:/customer/" + customerService.registrationCustomer(customerDto).getId();
    }

}
