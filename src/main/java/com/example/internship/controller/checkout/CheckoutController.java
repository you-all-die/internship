package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.*;
import com.example.internship.service.impl.CartServiceImpl;
import com.example.internship.service.impl.CustomerServiceImpl;
import com.example.internship.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Sergey Lapshin
 */

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CheckoutController {

    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;


    //Переход на страницу оформления заказа из корзины
    @GetMapping("/checkout")
    public String getCheckout(Model model) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на страницу регистрации
        if (customerId.isEmpty()) return "redirect:/registration";

        //Ищем пользователя по Id
        Optional<Customer> optionalCustomer = customerService.getById(customerId.get());

        Cart cart = optionalCustomer.get().getCart();

        System.out.println("CART: " + cart);
//        System.out.println("CART: " + cart);
//        Корзина создается при переходе по /cart
        if (cart == null) return "redirect:/cart";


        CustomerDto customer = customerService.getCustomerDto(optionalCustomer.get());
                model.addAttribute("customer", customer);
        return "cart/checkout";

    }

    // Оформление заказа
    @PostMapping("/checkout/add")
    public String postCheckout(@Validated CheckoutForm checkoutForm, BindingResult bindingResult, Model model) {

        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на страницу регистрации
        if (customerId.isEmpty()) return "redirect:/registration";

        Optional<Customer> customerOptional = customerService.getById(customerId.get());
        Customer customer = customerOptional.get();

        //Если передали пустые обязательные поля
        if (bindingResult.hasErrors()) {
            CustomerDto customerDto = customerService.getCustomerDto(customer);
            model.addAttribute("customer", customerDto);
            return "cart/checkout";
        }

        List<OrderLine> orderLines = customer.getCart().getOrderLines();

        orderService.makeOrder(customer, checkoutForm, orderLines);

        return "/home/index";
    }
}
