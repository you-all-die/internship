package com.example.internship.controller.order;

import com.example.internship.dto.CustomerDto;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(OrderController.BASE_MAPPING)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_MAPPING = "/order";
    private static final String TEMPLATE_DIR = "/order";

    private final CustomerService customerService;

    /**
     * Покупатель регистрируется или входит в систему.
     */
    @GetMapping
    public String enterToSystem(
            Authentication authentication,
            Model model
    ) {
        if (authentication.isAuthenticated()) {
            final Optional<CustomerDto> optionalCustomerDto = customerService.getFromAuthentication(authentication);
            model.addAttribute("customer", optionalCustomerDto.orElse(new CustomerDto()));
        }
        return TEMPLATE_DIR + "/enter";
    }

    /**
     * Покупатель выбирает способ доставки заказа и, если надо, адрес.
     */
    @GetMapping("/shipping")
    public String chooseShippingMethod() {
        return TEMPLATE_DIR + "/shipping";
    }

    /**
     * Покупатель выбирает способ оплаты заказа и вводит данные карты, если необходимо.
     */
    @GetMapping("/payment")
    public String choosePaymentMethod() {
        return TEMPLATE_DIR + "/payment";
    }

    /**
     * Покупатель проверяет данные заказа и подтверждает его.
     */
    @GetMapping("/confirm")
    public String confirmOrder() {
        return TEMPLATE_DIR + "/confirm";
    }
}
