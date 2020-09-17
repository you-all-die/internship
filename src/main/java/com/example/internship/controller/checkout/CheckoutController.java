package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.*;
import com.example.internship.security.CustomerPrincipal;
import com.example.internship.service.CartService;
import com.example.internship.service.CheckoutService;
import com.example.internship.service.impl.CartServiceImpl;
import com.example.internship.service.impl.CheckoutServiceImpl;
import com.example.internship.service.impl.CustomerServiceImpl;
import com.example.internship.service.item.ItemService;
import com.example.internship.service.item.ItemServiceImpl;
import com.example.internship.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private final OrderServiceImpl orderService;
    private final ItemServiceImpl itemService;


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
    public String postCheckout(@RequestParam Map<String, String> allParams) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на страницу регистрации
        if (customerId.isEmpty()) return "redirect:/registration";

        Optional<Customer> customerOptional = customerService.getById(customerId.get());
        Customer customer = customerOptional.get();

        List<OrderLine> orderLines = customer.getCart().getOrderLines();

        Order savedOrder = orderService.makeOrder(customer, allParams);

        for (OrderLine orderLine: orderLines) {
            itemService.makeItem(orderLine, savedOrder);
        }

        return "/home/index";
    }
}
