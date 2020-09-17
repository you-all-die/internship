package com.example.internship.controller.checkout;

import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Sergey Lapshin
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CheckoutController {

    private final CustomerService customerService;

    //Переход на страницу оформления заказа из корзины
    @GetMapping("/checkout")
    public String getCheckout(
            HttpServletRequest request,
            Model model
    ) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie(request);
        //Если куки нет, перенаправление на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart/checkout";

        //Ищем пользователя по Id
        Optional<Customer> optionalCustomer = customerService.getById(customerId.get());
        Cart cart = optionalCustomer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getCart();
        if (cart == null) return "redirect:/cart";


        model.addAttribute("customer", optionalCustomer.get());
        return "cart/checkout";
    }

}

