package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Item;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.repository.OrderRepository;
import com.example.internship.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author  Sergey Lapshin
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Override
    public OrderDto makeOrder(Customer customer, CheckoutForm checkoutForm) {
        Order order = new Order();
        List<Item> items = new ArrayList<>();
        List<OrderLine> orderLines = customer.getCart().getOrderLines();

        //        Обязательные поля (контролируются формой)
        order.setCustomerFirstName(checkoutForm.getFirstName());
        order.setCustomerLastName(checkoutForm.getLastName());
        order.setCustomerEmail(checkoutForm.getEmail());
        order.setAddressRegion(checkoutForm.getRegion());
        order.setAddressCity(checkoutForm.getCity());
        order.setAddressStreet(checkoutForm.getStreet());
        order.setAddressHouse(checkoutForm.getHouse());
        order.setAddressApartment(checkoutForm.getApartment());

        //        Необязательные поля
        order.setCustomerMiddleName(checkoutForm.getMiddleName() != null ? checkoutForm.getMiddleName() : "");
        order.setCustomerPhone(checkoutForm.getPhone() != null ? checkoutForm.getPhone() : "");
        order.setAddressDistrict(checkoutForm.getDistrict() != null ? checkoutForm.getDistrict() : "");
        order.setAddressComment(checkoutForm.getComment() != null ? checkoutForm.getComment() : "");

        order.setStatus("CREATED");
        order.setCustomerId(customer.getId());

        for (OrderLine orderLine: orderLines) {
            Item item = new Item();

            item.setOrder(order);
            item.setItemCategoryId(orderLine.getProduct().getCategory().getId());
            item.setItemDescription(orderLine.getProduct().getDescription());
            item.setItemName(orderLine.getProduct().getName());
            item.setItemPicture(orderLine.getProduct().getPicture());
            item.setItemPrice(orderLine.getProduct().getPrice());
            item.setItemQuantity(orderLine.getProductQuantity());

            items.add(item);
        }

        order.setItems(items);
        order.setDate(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(order);
        System.out.println(order.getCustomerId());
        cartService.removeAll(order.getCustomerId());

        OrderDto orderDto = convertToDto(order);

        return orderDto;
    }

    private OrderDto convertToDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

}
