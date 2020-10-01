package com.example.internship.controller.order;

import com.example.internship.dto.CustomerDto;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(OrderController.BASE_MAPPING)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_MAPPING = "/order";

    private static final String ENTER_MAPPING = "/enter";
    private static final String SHIPPING_MAPPING = "/shipping";
    private static final String PAYMENT_MAPPING = "/payment";
    private static final String CONFIRM_MAPPING = "/confirm";

    private static final String TEMPLATE_DIR = "/order";

    private static final String ENTER_TEMPLATE = TEMPLATE_DIR + "/enter";
    private static final String SHIPPING_TEMPLATE = TEMPLATE_DIR + "/shipping";
    private static final String PAYMENT_TEMPLATE = TEMPLATE_DIR + "/payment";
    private static final String CONFIRM_TEMPLATE = TEMPLATE_DIR + "/confirm";

    private final CustomerService customerService;

    /**
     * Покупатель регистрируется или входит в систему.
     */
    @GetMapping(ENTER_MAPPING)
    public String enterToSystem(
            Authentication authentication,
            Model model
    ) {
        if (null != authentication && authentication.isAuthenticated()) {
            final Optional<CustomerDto> optionalCustomerDto = customerService.getFromAuthentication(authentication);
            model.addAttribute("customer", optionalCustomerDto.orElse(new CustomerDto()));
        } else {
            model.addAttribute("customer", new CustomerDto());
        }
        return ENTER_TEMPLATE;
    }

    @PostMapping
    public String submitCustomerData() {
        return "redirect:" + BASE_MAPPING + SHIPPING_MAPPING;
    }

    /**
     * Покупатель выбирает способ доставки заказа и, если надо, адрес.
     */
    @GetMapping(SHIPPING_MAPPING)
    public String chooseShippingMethod() {
        return SHIPPING_TEMPLATE;
    }

    /**
     * Покупатель выбирает способ оплаты заказа и вводит данные карты, если необходимо.
     */
    @GetMapping(PAYMENT_MAPPING)
    public String choosePaymentMethod() {
        return PAYMENT_TEMPLATE;
    }

    /**
     * Покупатель проверяет данные заказа и подтверждает его.
     */
    @GetMapping(CONFIRM_MAPPING)
    public String confirmOrder() {
        return CONFIRM_TEMPLATE;
    }
}
