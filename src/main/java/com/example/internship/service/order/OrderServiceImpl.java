package com.example.internship.service.order;

import com.example.internship.entity.Customer;
import com.example.internship.entity.Order;
import com.example.internship.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public Order makeOrder(Customer customer, Map<String, String> allParams) {
        //        Создание заказа
        Order order = new Order();

        //        Обязательные поля (контролируются формой)
        order.customerFirstName = allParams.get("firstName");
        order.customerLastName = allParams.get("lastName");
        order.addressRegion = allParams.get("region");
        order.addressCity = allParams.get("city");
        order.addressStreet = allParams.get("street");
        order.addressHouse = allParams.get("house");
        order.addressApartment = allParams.get("apartment");

        //        Необязательные поля
        order.customerMiddleName = allParams.containsKey("middleName") ? allParams.get("middleName") : "";
        order.customerEmail = allParams.containsKey("email") ? allParams.get("email") : "";
        order.customerPhone = allParams.containsKey("phone") ? allParams.get("phone") : "";
        order.addressDistrict = allParams.containsKey("district") ? allParams.get("district") : "";
        order.addressComment = allParams.containsKey("comment") ? allParams.get("comment") : "";

        //        Берем значения из других таблиц
        order.customerId = customer.getId();

//          Создать таблицу со статусами (сделать)
        order.statusId = 12L;

//                Сдесь записываем адрес в базу данных и берем его ID (сделать)
        order.addressId = 12312L;

        System.out.println("ORDER TO SAVE: " + order);
        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }
}
