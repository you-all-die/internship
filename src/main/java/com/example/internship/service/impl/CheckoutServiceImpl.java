package com.example.internship.service.impl;

import com.example.internship.entity.Order;
import com.example.internship.repository.CheckoutRepository;
import com.example.internship.service.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;

    @Override
    public boolean addOrder(Order order) {
        System.out.println("ORDER TO SAVE: " + order);
        checkoutRepository.save(order);
        return true;
    }
}
