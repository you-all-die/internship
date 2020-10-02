package com.example.internship.controller.customer;

import com.example.internship.exception.EntityNotFoundException;
import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.ItemDto;
import com.example.internship.dto.OrderDto;
import com.example.internship.refactoringdto.AddressDto;
import com.example.internship.service.address.AddressService;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping(CustomerController.BASE_MAPPING)
@RequiredArgsConstructor
public class CustomerController {

    public static final String BASE_MAPPING = "/customer";

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderService orderService;

    @GetMapping
    public String viewCustomerProfile(Authentication authentication, Model model) {
        CustomerDto customer = customerService.getFromAuthentication(authentication).orElse(null);

        if (Objects.isNull(customer)) {
            throw EntityNotFoundException.customerNotFound();
        }

        List<OrderDto> orders = orderService.findAllByCustomerId(customer.getId());
        List<AddressDto> addresses = addressService.getAllByCustomerId(customer.getId());

        model.addAttribute("customer", customer);
        model.addAttribute("addresses", addresses);
        model.addAttribute("orders", orders);

        return "customer/view";
    }

    @GetMapping("/edit")
    public String editCustomer(Authentication authentication, Model model) {
        Optional<CustomerDto> customerOptional = customerService.getFromAuthentication(authentication);
        if (customerOptional.isPresent()) {
            CustomerDto customerDto = customerOptional.get();
            model.addAttribute("customer", customerDto);
            return BASE_MAPPING + "/profile";
        }
        throw EntityNotFoundException.customerNotFound();
    }

    @PostMapping
    public String saveCustomer(@ModelAttribute CustomerDto customer) {
        customerService.save(customer);
        return "redirect:" + BASE_MAPPING;
    }

    @GetMapping("/order/{id}")
    public String showOrder(@PathVariable("id") Long orderId, Authentication authentication, Model model) {
        OrderDto order = orderService.findByOrderId(orderId, authentication);

        if (Objects.isNull(order)) {
            throw EntityNotFoundException.orderNotFound();
        }

        List<ItemDto> items = order.getItems();
        BigDecimal totalPrice = orderService.getTotalPrice(items);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        model.addAttribute("totalPrice", totalPrice);

        return "customer/showOrder";
    }
}
