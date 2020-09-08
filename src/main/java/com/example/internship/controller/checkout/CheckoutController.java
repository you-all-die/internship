package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergey Lapshin
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CheckoutController {

    @Autowired
    private CustomerServiceImpl customerService;

    //Переход на страницу оформления заказа из корзины
    @GetMapping("/checkout")
    public String getCheckout(Model model) {
        //        Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
//        Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart/checkout";

        //Ищем пользователя по Id
        Optional<Customer> optionalCustomer = customerService.getById(Long.valueOf(customerId.get()));
        //Если пользователь есть, возвращаем его, если нет, создаем нового
        CustomerDto customer = optionalCustomer.isPresent() ? customerService.getCustomerDto(optionalCustomer.get()) : customerService.createAnonymousCustomer();
        model.addAttribute("customer", customer);
        return "cart/checkout";
    }

    // Оформление заказа (куда передаем заказ?)
//    @PostMapping("/checkout")
//    public String postCheckout(Model model,
//                               @RequestParam("cusomerId") Long Id) {
//        Optional<Customer> optionalCustomer = customerService.getById(Id);
//        model.addAttribute("isSuccess", true);
//        return "/cart/checkout";
//    }
}
