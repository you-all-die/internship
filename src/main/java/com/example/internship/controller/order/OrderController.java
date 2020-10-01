package com.example.internship.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(OrderController.BASE_MAPPING)
public class OrderController {

    public static final String BASE_MAPPING = "/order";
    private static final String TEMPLATE_DIR = "/order";

    /**
     * Показать заказ
     */
    @GetMapping("/show")
    public String showOrder() {
        return TEMPLATE_DIR + "/show";
    }

    /**
     * Покупатель регистрируется или входит в систему
     */
    @GetMapping("/enter")
    public String enterToSystem() {
        return TEMPLATE_DIR + "/enter";
    }

    /**
     * Покупатель выбирает способ доставки заказа и, если надо, адрес
     */
    @GetMapping("/shipping")
    public String chooseShippingMethod() {
        return TEMPLATE_DIR + "/shipping";
    }

    /**
     * Покупатель проверяет данные заказа и подтверждает его
     */
    @GetMapping("/confirm")
    public String confirmOrder() {
        return TEMPLATE_DIR + "/confirm";
    }
}
