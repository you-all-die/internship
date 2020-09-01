package com.example.internship.service.impl;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;
import com.example.internship.repository.CartRepository;
import com.example.internship.service.CartService;
import com.example.internship.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Modenov D.A
 */
@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CustomerService customerService;

    private final CartRepository cartRepository;

    private final ModelMapper mapper;

    @Override
    public boolean add(Product product, String customerId) {
        Optional<Customer> customer = customerService.checkCustomerCart(customerId);

        if (customer.isEmpty()) return false;

        Cart cart = customer.get().getCart();
        List<OrderLine> orderLines = cart.getOrderLines();
        Optional<OrderLine> checkOrderLine = getOrderLineByProduct(orderLines, product);

        if (checkOrderLine.isPresent()) {
           cart.setOrderLines(updateProductQuantity(orderLines, checkOrderLine.get(),
                   checkOrderLine.get().getProductQuantity() + 1));
           cartRepository.save(cart);
            return true;
        }

        cart.getOrderLines().add(new OrderLine(null, product, 1));
        cartRepository.save(cart);
        return true;
    }

    @Override
    public boolean updateQuantity(Product product, Integer productQuantity, String customerId) {
        Optional<Customer> customer = customerService.checkCustomerCart(customerId);

        if (customer.isEmpty() || productQuantity <= 0) return false;

        Cart cart = customer.get().getCart();
        List<OrderLine> orderLines = cart.getOrderLines();
        Optional<OrderLine> checkOrderLine = getOrderLineByProduct(orderLines, product);

        if (checkOrderLine.isPresent()) {
            cart.setOrderLines(updateProductQuantity(orderLines, checkOrderLine.get(), productQuantity));
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Product product, String customerId) {
        Optional<Customer> customer = customerService.checkCustomerCart(customerId);;

        if (customer.isEmpty()) return false;

        Cart cart = customer.get().getCart();
        Optional<OrderLine> orderLine = getOrderLineByProduct(cart.getOrderLines(), product);

        if (orderLine.isPresent()) {
            cart.getOrderLines().remove(orderLine.get());
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    @Override
    public List<OrderLineDto> findAll(String customerId) {
        Optional<Customer> customer =  customerService.checkCustomerCart(customerId);

        if (customer.isEmpty()) return new ArrayList<>();

        return customer.get().getCart().getOrderLines().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalPrice(String customerId) {
        Optional<Customer> customer = customerService.checkCustomerCart(customerId);

        if (customer.isEmpty()) return BigDecimal.ZERO;

        List<OrderLine> orderLines = customer.get().getCart().getOrderLines();

        return orderLines.stream()
                .filter(value -> value.getProduct().getPrice() != null &&
                        value.getProduct().getPrice().compareTo(BigDecimal.ZERO) > 0 )
                .map(value -> value.getProduct().getPrice().multiply(BigDecimal.valueOf(value.getProductQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderLineDto convertToDto(OrderLine orderLine) {
        return mapper.map(orderLine, OrderLineDto.class);
    }

    private Optional<OrderLine> getOrderLineByProduct(List<OrderLine> orderLines, Product product) {
       return orderLines.stream().filter(orderLine -> orderLine.getProduct().equals(product)).findFirst();
    }

    private List<OrderLine> updateProductQuantity(List<OrderLine> orderLines, OrderLine orderLine,
                                                  Integer productQuantity) {
        return orderLines.stream().peek(value -> {
                if (value.equals(orderLine)) {
                    value.setProductQuantity(productQuantity);
                }
        }).collect(Collectors.toList());
    }
}
