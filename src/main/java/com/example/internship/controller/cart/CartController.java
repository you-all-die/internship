package com.example.internship.controller.cart;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Product;
import com.example.internship.service.cart.CartService;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Modenov D.A
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;

    @GetMapping
    public String showCart(Model model) {
//        Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
//        Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart";

        List<OrderLineDto> orderLines = cartService.findAll(Long.valueOf(customerId.get()));
        BigDecimal totalPrice = cartService.getTotalPrice(Long.valueOf(customerId.get()));

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderLines", orderLines);
        return "cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Product product)  {
//        Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
//        Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart";

        cartService.add(product, customerId.get());
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam("productId") Product product) {
//        Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
//        Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart";

        cartService.remove(product, customerId.get());
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateItem(@RequestParam("productId") Product product,
                             @RequestParam("productQuantity") Integer productQuantity) {
        //        Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
//        Если куки нет, редирект на эту же страницу, чтобы кука (установленная через фильтр) записалась в браузер через response
        if (customerId.isEmpty()) return "redirect:/cart";

        cartService.updateQuantity(product, productQuantity, customerId.get());
        return "redirect:/cart";
    }
}
