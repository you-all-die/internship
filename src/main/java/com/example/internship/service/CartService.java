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

    boolean add(Product product, Long customerId);

    boolean updateQuantity(Product product,Integer productQuantity, Long customerId);

    boolean remove(Product product, Long customerId);

    List<OrderLineDto> findAll(Long customerId);

    BigDecimal getTotalPrice(Long customerId);
}
