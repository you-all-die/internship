package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;

import java.util.List;

/**
 * @author  Sergey Lapshin
 */

public interface OrderService {
    public Order makeOrder(Customer customer, CheckoutForm checkoutForm, List<OrderLine> orderLines);
}
