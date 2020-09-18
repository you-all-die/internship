package com.example.internship.service.cart;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;
import com.example.internship.repository.CartRepository;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final ModelMapper mapper;

    @Override
    public boolean add(Long productId, Long customerId) {
        Optional<Product> product = productRepository.findById(productId);

        return product.filter(value -> add(value, customerId)).isPresent();
    }

    @Override
    public boolean add(Product product, Long customerId) {
        Optional<Customer> customer = checkCustomerCart(customerId);

        if (customer.isEmpty() || Objects.isNull(product)) return false;

        Cart cart = customer.get().getCart();
        List<OrderLine> orderLines = cart.getOrderLines();
        Optional<OrderLine> checkOrderLine = getOrderLineByProduct(orderLines, product);

        if (checkOrderLine.isPresent()) {
            checkOrderLine.get().setProductQuantity(checkOrderLine.get().getProductQuantity() + 1);
            cartRepository.save(cart);
            return true;
        }

        OrderLine orderLine = new OrderLine(null, cart, product, 1);
        cart.getOrderLines().add(orderLine);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public boolean updateQuantity(Long productId, Integer productQuantity, Long customerId) {
        Optional<Product> product = productRepository.findById(productId);

        return product.filter(value -> updateQuantity(value, productQuantity, customerId)).isPresent();
    }

    @Override
    public boolean updateQuantity(Product product, Integer productQuantity, Long customerId) {
        Optional<Customer> customer = checkCustomerCart(customerId);

        if (customer.isEmpty() || productQuantity <= 0) return false;

        Cart cart = customer.get().getCart();
        List<OrderLine> orderLines = cart.getOrderLines();
        Optional<OrderLine> checkOrderLine = getOrderLineByProduct(orderLines, product);

        if (checkOrderLine.isPresent()) {
            checkOrderLine.get().setProductQuantity(productQuantity);
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Long productId, Long customerId) {
        Optional<Product> product = productRepository.findById(productId);

        return product.filter(value -> remove(value, customerId)).isPresent();
    }

    @Override
    public boolean remove(Product product, Long customerId) {
        Optional<Customer> customer = checkCustomerCart(customerId);

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
    public boolean removeAll(Long customerId) {
        Optional<Customer> customer = checkCustomerCart(customerId);

        if (customer.isEmpty()) return false;

        Cart cart = customer.get().getCart();
        cart.setOrderLines(null);
        cartRepository.save(cart);

        return true;
    }

    @Override
    public List<OrderLineDto> findAll(Long customerId) {
        Optional<Customer> customer = checkCustomerCart(customerId);

        if (customer.isEmpty()) return new ArrayList<>();

        return customer.get().getCart().getOrderLines().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalPrice(Long customerId) {
        Optional<Customer> customer = checkCustomerCart(customerId);

        if (customer.isEmpty()) return BigDecimal.ZERO;

        List<OrderLine> orderLines = customer.get().getCart().getOrderLines();

        return orderLines.stream()
                .filter(value -> value.getProduct().getPrice() != null &&
                        value.getProduct().getPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(value -> value.getProduct().getPrice().multiply(BigDecimal.valueOf(value.getProductQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Проверят существует ли пользователь и есть ли у него корзина.
     *
     * @param customerId id пользователя.
     * @return возвращает пользователя, если существует.
     */
    private Optional<Customer> checkCustomerCart(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) return Optional.empty();

        if (customer.get().getCart() == null) {
            log.error("Cart for customer: " + customerId + " not exist!");
            Cart cart = new Cart();
            cart.setOrderLines(new ArrayList<>());
            cart.setCustomer(customer.get());
            customer.get().setCart(cart);
            return Optional.of(customerRepository.save(customer.get()));
        }

        if (customer.get().getCart().getOrderLines() == null) {
            return Optional.empty();
        }

        return customer;
    }

    /**
     * Конвертирует линии заказов в DTO.
     *
     * @param orderLine линия заказа.
     * @return линия заказа конвертированная в DTO.
     */
    private OrderLineDto convertToDto(OrderLine orderLine) {
        return mapper.map(orderLine, OrderLineDto.class);
    }

    /**
     * Проверяет есть ли продукт в корзине.
     *
     * @param orderLines заказы в корзине
     * @param product    продукт который мы хотим проверить.
     * @return линия заказа с товаром из корзины, если существует.
     */
    private Optional<OrderLine> getOrderLineByProduct(List<OrderLine> orderLines, Product product) {
        return orderLines.stream().filter(orderLine -> orderLine.getProduct().equals(product)).findFirst();
    }
}
