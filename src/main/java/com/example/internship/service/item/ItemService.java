package com.example.internship.service.item;

import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;

import java.util.List;

public interface ItemService {
    public boolean makeItem(OrderLine orderLine, Order savedOrder);
}
