package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.CheckoutService;
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

