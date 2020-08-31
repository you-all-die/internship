package com.example.internship.service;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Modenov D.A
 */
public interface CartService {

    boolean add(Product product, String customerId);

    boolean updateQuantity(Product product,Integer productQuantity, String customerId);

    boolean remove(Product product, String customerId);

    List<OrderLineDto> findAll(String customerId);

    BigDecimal getTotalPrice(String customerId);
}
