package com.example.internship.service.order;

import com.example.internship.entity.Customer;
import com.example.internship.entity.Order;

import java.util.Map;

public interface OrderService {
    public Order makeOrder(Customer customer, Map<String, String> allParams);
}
