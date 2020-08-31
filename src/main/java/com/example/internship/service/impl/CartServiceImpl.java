package com.example.internship.service.impl;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;
import com.example.internship.repository.CartRepository;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.service.CartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Modenov D.A
 */
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CustomerRepository customerRepository;

    private final CartRepository cartRepository;

    private final ModelMapper mapper;

    @Override
    public boolean add(Product product, String customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(customerId));

        if (customer.isEmpty() || customer.get().getCart() == null) return false;

        Cart cart = customer.get().getCart();

        if (cart.getOrderLines().stream().filter(orderLine -> orderLine.getProduct().equals(product))
                                         .findFirst().orElse(null) != null) {

            cart.setOrderLines(cart.getOrderLines().stream().peek(orderLine -> {
                if (orderLine.getProduct().equals(product)) {
                    orderLine.setProductQuantity(orderLine.getProductQuantity() + 1);
                }
            }).collect(Collectors.toList()));
            cartRepository.save(cart);
            return true;
        }

        cart.getOrderLines().add(OrderLine.builder().product(product).productQuantity(1).build());
        cartRepository.save(cart);

        return true;
    }

    @Override
    public boolean updateQuantity(Product product,Integer productQuantity, String customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(customerId));

        if (customer.isEmpty() || productQuantity <= 0) return false;

        Cart cart = customer.get().getCart();

        if (cart.getOrderLines().stream().filter(orderLine -> orderLine.getProduct().equals(product))
                .findFirst().orElse(null) != null) {

            cart.setOrderLines(cart.getOrderLines().stream().peek(orderLine -> {
                if (orderLine.getProduct().equals(product)) {
                    orderLine.setProductQuantity(productQuantity);
                }
            }).collect(Collectors.toList()));
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Product product, String customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(customerId));

        if (customer.isEmpty()) return false;

        Cart cart = customer.get().getCart();

        Optional<OrderLine> orderLine = cart.getOrderLines()
                .stream().filter(value -> value.getProduct().equals(product)).findFirst();

        if (orderLine.isPresent()) {
            cart.getOrderLines().remove(orderLine.get());
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    @Override
    public List<OrderLineDto> findAll(String customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(customerId));

        if (customer.isEmpty() || customer.get().getCart() == null) return new ArrayList<>();

        return customer.get().getCart().getOrderLines().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalPrice(String customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.valueOf(customerId));

        if (customer.isEmpty() || customer.get().getCart() == null) return BigDecimal.ZERO;

        List<OrderLine> orderLines = customer.get().getCart().getOrderLines();

        return orderLines.stream()
                .map(value -> value.getProduct().getPrice().multiply(BigDecimal.valueOf(value.getProductQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderLineDto convertToDto(OrderLine orderLine) {
        return mapper.map(orderLine, OrderLineDto.class);
    }
}
