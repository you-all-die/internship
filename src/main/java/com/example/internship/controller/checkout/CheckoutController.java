package com.example.internship.controller.checkout;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.mail.exception.MailServiceException;
import com.example.internship.mail.service.EmailService;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Sergey Lapshin
 */

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final EmailService emailService;

    //Переход на страницу оформления заказа из корзины
    @GetMapping("/checkout")
    public String getCheckout(CheckoutForm checkoutForm, final Model model) {
        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        System.out.println("CUSTOMERID: " + customerId.get());
        //Если куки нет, редирект на страницу регистрации
        if (customerId.isEmpty()) {
            return "redirect:/registration";
        }

        //Ищем пользователя по Id
        Optional<Customer> optionalCustomer = customerService.getById(customerId.get());
        System.out.println("CUSTOMER: " + optionalCustomer.get().getEmail());

        Cart cart = optionalCustomer.get().getCart();

        if (cart == null) {
            return "redirect:/cart";
        }

        CustomerDto customerDto = customerService.convertToDto(optionalCustomer.get());
//        CustomerDto customerDto = customerService.getDtoById(customerId.get()).get();
        model.addAttribute("customer", customerDto);
        System.out.println("CUSTOMERDTO: " + customerDto.getEmail());
        return "cart/checkout";

    }

    // Оформление заказа
    @PostMapping("/checkout")
    public String postCheckout(@Valid CheckoutForm checkoutForm, BindingResult bindingResult, Model model) {

        //Получение куки customerID
        Optional<Long> customerId = customerService.customerIdFromCookie();
        //Если куки нет, редирект на страницу регистрации
        if (customerId.isEmpty()) return "redirect:/registration";

        Optional<Customer> customerOptional = customerService.getById(customerId.get());
        Customer customer = customerOptional.get();
        CustomerDto customerDto = customerService.convertToDto(customer);

        //Если передали пустые обязательные поля
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customerDto);
            return "cart/checkout";
        }

        OrderDto orderDto = orderService.makeOrder(customer, checkoutForm);
        if (orderDto != null) {
            try {
                emailService.sendOrderDetailsMessage(customerDto, orderDto);
            } catch (MailServiceException e) {
                log.info("Can't send order details email");
            }
        }

        return "redirect:/";
    }
}
