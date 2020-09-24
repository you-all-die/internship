package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.impl.EmailServiceImpl;
import com.example.internship.service.customer.CustomerServiceImpl;
import com.example.internship.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private final OrderServiceImpl orderService;
    private final EmailServiceImpl emailService;

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
        CustomerDto customerDto = customerService.getCustomerDto(customer);

        //Если передали пустые обязательные поля
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customerDto);
            return "cart/checkout";
        }

        List<OrderLine> orderLines = customer.getCart().getOrderLines();

        Order order = orderService.makeOrder(customer, checkoutForm, orderLines);
        OrderDto orderDto = orderService.convertToDto(order);
        if (order != null) {
            try {
                emailService.sendOrderDetailsMessage(customerDto, orderDto);
            } catch (MailServiceException e) {
                e.printStackTrace();
            }
        }

        return "/home/index";
    }
}
