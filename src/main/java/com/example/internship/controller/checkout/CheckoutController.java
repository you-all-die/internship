package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.*;
import com.example.internship.security.CustomerPrincipal;
import com.example.internship.service.CartService;
import com.example.internship.service.CheckoutService;
import com.example.internship.service.impl.CartServiceImpl;
import com.example.internship.service.impl.CheckoutServiceImpl;
import com.example.internship.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergey Lapshin
 */

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CheckoutController {

    private final CustomerServiceImpl customerService;
    private final CheckoutServiceImpl checkoutService;
    private final CartServiceImpl cartService;

    //Переход на страницу оформления заказа из корзины
    @GetMapping("/checkout")
    public String getCheckout(Model model) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart/checkout";

        //Ищем пользователя по Id
        Optional<Customer> optionalCustomer = customerService.getById(customerId.get());
        //Если пользователь есть, возвращаем его, если нет, создаем нового
        CustomerDto customer = optionalCustomer.isPresent() ? customerService.getCustomerDto(optionalCustomer.get()) : customerService.createAnonymousCustomer();
        model.addAttribute("customer", customer);
        return "cart/checkout";
    }

    // Оформление заказа
    @PostMapping("/checkout/add")
    public String postCheckout(@RequestParam Map<String, String> allParams) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart/checkout";

        Optional<Customer> customer = customerService.getById(customerId.get());
//        Cart cart = cartService.getByCustomerId(customerId.get());

        System.out.println("CUSTOMER: " + customer.get());

//        Создание заказа
        Order order = new Order();

//        Обязательные поля (контролируются формой)
        order.customerFirstName = allParams.get("firstName");
        order.customerLastName = allParams.get("lastName");
//        order.address = allParams.get("address");

//        Необязательные поля
        order.customerMiddleName = allParams.containsKey("middleName") ? allParams.get("middleName") : "";
        order.customerEmail = allParams.containsKey("email") ? allParams.get("email") : "";
        order.customerPhone = allParams.containsKey("phone") ? allParams.get("phone") : "";

//        Берем значения из других таблиц
        order.customerId = customer.get().getId();
        order.statusId = 12L;
//        order.orderLines = customer.get().getCart().getOrderLines();

//        Order order = new Order(customer.get());
        System.out.println("ORDER: " + order);

//
        checkoutService.addOrder(order);
        System.out.println("Отправили тест ордер");
        return "/home/index";
    }
}
