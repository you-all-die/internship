package com.example.internship.service.order;

import com.example.internship.entity.Customer;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;

import java.util.List;
import java.util.Map;

/**
 * @author  Sergey Lapshin
 */

public interface OrderService {
    public boolean makeOrder(Customer customer, Map<String, String> allParams, List<OrderLine> orderLines);
}
