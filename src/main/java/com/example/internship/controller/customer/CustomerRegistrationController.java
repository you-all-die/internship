package com.example.internship.controller.customer;

import com.example.internship.dto.CustomerDto;
import com.example.internship.service.CustomerService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class CustomerRegistrationController {

    private CustomerService customerService;

    @GetMapping
    public String registration(CustomerDto customerDto) {

        return "customer/registration";
    }

    // Регистрация нового покупателя
    @PostMapping
    public String newCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult, Model model,
                              HttpServletRequest request, HttpServletResponse response) {
        // Если неверно заполнены поля
        if (bindingResult.hasErrors()) {
            return "customer/registration";
        }
        // Если пароли не совпадают
        if (!request.getParameter("password").equals(request.getParameter("passwordConfirm"))) {
            model.addAttribute("errorMessage", "Пароли не совпадают.");
            return "customer/registration";
        }

        // регистрация покупателя и редирект на страницу его профиля
        return "redirect:/customer?id=" + customerService.registrationCustomer(customerDto).getId();
    }

}
