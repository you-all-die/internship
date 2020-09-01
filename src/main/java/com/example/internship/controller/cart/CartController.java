package com.example.internship.controller.cart;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Modenov D.A
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String showCart(@CookieValue(value = "customerId", defaultValue = "") String customerId, Model model) {
        List<OrderLineDto> orderLines = cartService.findAll(Long.valueOf(customerId));
        BigDecimal totalPrice = cartService.getTotalPrice(Long.valueOf(customerId));

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderLines", orderLines);
        return "cart/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@CookieValue(value = "customerId", defaultValue = "") String customerId,
                             @RequestParam("productId") Product product) {

        cartService.remove(product, Long.valueOf(customerId));

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateItem(@CookieValue(value = "customerId", defaultValue = "") String customerId,
                             @RequestParam("productId") Product product,
                             @RequestParam("productQuantity") Integer productQuantity) {

        cartService.updateQuantity(product, productQuantity, Long.valueOf(customerId));
        return "redirect:/cart";
    }
}
