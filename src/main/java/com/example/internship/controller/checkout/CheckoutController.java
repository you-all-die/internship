package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.service.customer.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author Sergey Lapshin
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CheckoutController {

    //TODO: Используется имплементация сервиса, а не его интерфейс. Нужно переделать на интерфейс.
    private final CustomerServiceImpl customerService;
//    private final CheckoutService checkoutService;

    //Переход на страницу оформления заказа из корзины
    @GetMapping("/checkout")
    public String getCheckout(Model model) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart/checkout";
//        System.out.println("CUSTOMER ID NOT EMPTY");

        //Ищем пользователя по Id
        Optional<Customer> optionalCustomer = customerService.getById(customerId.get());
        Cart cart = optionalCustomer.get().getCart();
//        System.out.println("CART: " + cart);
//        Корзина создается при переходе по /cart
        if (cart == null) return "redirect:/cart";


        CustomerDto customer = customerService.getCustomerDto(optionalCustomer.get());
                model.addAttribute("customer", customer);
        return "cart/checkout";

    }

}

