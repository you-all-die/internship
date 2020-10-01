package com.example.internship.service.order;

import com.example.internship.controller.checkout.CheckoutForm;
import com.example.internship.dto.OrderDto;
import com.example.internship.entity.Customer;
import com.example.internship.entity.Item;
import com.example.internship.entity.Order;
import com.example.internship.entity.OrderLine;
import com.example.internship.repository.OrderRepository;
import com.example.internship.service.cart.CartService;
import com.example.internship.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author  Sergey Lapshin
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final CustomerService customerService;

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
        cartService.removeAll(order.getCustomerId());

        return convertToDto(order);
    }

    @Override
    public OrderDto findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(this::convertToDto).orElse(null);
    }

    @Override
    public OrderDto addOrderToCustomer(Long customerId, OrderDto orderDto) {
        if (customerService.existsById(customerId)) {
            Order order = mapper.map(orderDto, Order.class);
            order.setCustomerId(customerId);
            return mapper.map(orderRepository.save(order), OrderDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderDto> getCustomerOrders(Long customerId, Pageable pageable) {
        if (customerService.existsById(customerId)) {
            return orderRepository.findByCustomerId(customerId,
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "date"))
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private OrderDto convertToDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

}
