package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Customer;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * @author  Sergey Lapshin
 */

public interface OrderService {

    OrderDto makeOrder(Customer customer, CheckoutForm checkoutForm);

    List<OrderDto> findAllByCustomerId(Long customerId);

    OrderDto findByOrderId(Long orderId, Authentication authentication);

}
