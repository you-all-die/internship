package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author  Sergey Lapshin
 */

public interface OrderService {
    OrderDto makeOrder(Customer customer, CheckoutForm checkoutForm);
    OrderDto findById(Long id);
    OrderDto addOrderToCustomer(Long customerId, OrderDto orderDto);
    List<OrderDto> getCustomerOrders(Long customerId, Pageable pageable);
}
