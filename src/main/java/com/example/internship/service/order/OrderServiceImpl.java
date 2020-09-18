package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Item;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.repository.ItemRepository;
import com.example.internship.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author  Sergey Lapshin
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    public boolean makeOrder(Customer customer, CheckoutForm checkoutForm, List<OrderLine> orderLines) {
        //        Создание заказа и айтема
        Order order = new Order();

//        //        Обязательные поля (контролируются формой)
//        order.setCustomerFirstName(allParams.get("firstName"));
//        order.setCustomerLastName(allParams.get("lastName"));
//        order.setAddressRegion(allParams.get("region"));
//        order.setAddressCity(allParams.get("city"));
//        order.setAddressStreet(allParams.get("street"));
//        order.setAddressHouse(allParams.get("house"));
//        order.setAddressApartment(allParams.get("apartment"));
//
//        //        Необязательные поля
//        order.setCustomerMiddleName(allParams.containsKey("middleName") ? allParams.get("middleName") : "");
//        order.setCustomerEmail(allParams.containsKey("email") ? allParams.get("email") : "");
//        order.setCustomerPhone(allParams.containsKey("phone") ? allParams.get("phone") : "");
//        order.setAddressDistrict(allParams.containsKey("district") ? allParams.get("district") : "");
//        order.setAddressComment(allParams.containsKey("comment") ? allParams.get("comment") : "");


//                Обязательные поля (контролируются формой)
        order.setCustomerFirstName(checkoutForm.getFirstName());
        order.setCustomerLastName(checkoutForm.getLastName());
        order.setAddressRegion(checkoutForm.getRegion());
        order.setAddressCity(checkoutForm.getCity());
        order.setAddressStreet(checkoutForm.getStreet());
        order.setAddressHouse(checkoutForm.getHouse());
        order.setAddressApartment(checkoutForm.getApartment());

        //        Необязательные поля
        order.setCustomerMiddleName(checkoutForm.getMiddleName() != null ? checkoutForm.getMiddleName() : "");
        order.setCustomerEmail(checkoutForm.getEmail() != null ? checkoutForm.getEmail() : "");
        order.setCustomerPhone(checkoutForm.getPhone() != null ? checkoutForm.getPhone() : "");
        order.setAddressDistrict(checkoutForm.getDistrict() != null ? checkoutForm.getDistrict() : "");
        order.setAddressComment(checkoutForm.getComment() != null ? checkoutForm.getComment() : "");

        //        Берем значения из других таблиц
        order.setCustomerId(customer.getId());

//          Брать текстовой значение
        order.setStatus("CREATED");

        orderRepository.save(order);

        for (OrderLine orderLine: orderLines) {
            Item item = new Item();

            item.setOrder(order);
            item.setItemCategoryId(orderLine.getProduct().getCategory().getId());
            item.setItemDescription(orderLine.getProduct().getDescription());
            item.setItemName(orderLine.getProduct().getName());
            item.setItemPicture(orderLine.getProduct().getPicture());
            item.setItemPrice(orderLine.getProduct().getPrice());
            item.setItemQuantity(orderLine.getProductQuantity());

            itemRepository.save(item);
        }

        return true;
    }
}
