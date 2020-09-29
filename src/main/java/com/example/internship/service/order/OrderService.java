package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Customer;

/**
 * @author  Sergey Lapshin
 */

public interface OrderService {
    public OrderDto makeOrder(Customer customer, CheckoutForm checkoutForm);
}
