package com.example.internship.service.impl;

import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;
import com.example.internship.repository.CartRepository;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Modenov D.A
 */
@SpringBootTest
class CartServiceImplTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);

    private final CartRepository cartRepository = mock(CartRepository.class);

    private final ModelMapper mapper = mock(ModelMapper.class);

    private CartService cartService;

    private final Long CUSTOMER_ID1 = 1L;
    private final Long CUSTOMER_ID2 = 2L;

    private final Long ORDER_LINE_ID1 = 1L;

    Customer customer;

    Cart cart;

    Product product;
    Product product2;

    @BeforeEach
    public void before() {
        product = new Product();
        product.setId(1L);
        product2 = new Product();
        product2.setId(2L);

        cart = new Cart();
        cart.setId(1L);
        cart.setOrderLines(new ArrayList<>() {{
            add(new OrderLine(ORDER_LINE_ID1, product, 1));
        }});

        customer = new Customer();
        customer.setId(CUSTOMER_ID1);
        customer.setCart(cart);

        when(customerRepository.findById(eq(CUSTOMER_ID1))).thenReturn(Optional.of(customer));
        when(customerRepository.findById(eq(CUSTOMER_ID2))).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);
        cartService = new CartServiceImpl(customerRepository,cartRepository, mapper);
    }

    /**
     * Проверка провального метода add:
     * <br> - Входные параметры null.
     * <br> - Один из параметров null.
     * <br> - Несуществующий пользователь.
     */
    @Test
    public void testAddFail() {

        assertFalse(cartService.add(null, null));

        assertFalse(cartService.add(null, CUSTOMER_ID1));
        
        assertFalse(cartService.add(new Product(), CUSTOMER_ID2));

    }

    /**
     * Проверка успешного выполнения метода add:
     * <br> - Добавление продукта.
     * <br> - Добавление идентичного продукта.
     */
    @Test
    public void testAddSuccess() {

        assertTrue(cartService.add(product2, CUSTOMER_ID1));

        Optional<OrderLine> orderLine1 = cart.getOrderLines().stream()
                .filter(value -> value.getProduct().equals(product2))
                .findFirst();
        assertEquals(product2, orderLine1.get().getProduct());

        assertTrue(cartService.add(product2, CUSTOMER_ID1));

        assertEquals(2, orderLine1.get().getProductQuantity());

    }

    /**
     * Проверка провального метода updateQuantity:
     * <br> - Входные параметры null.
     * <br> - Несуществующий пользователь.
     * <br> - Продукта нет в корзине.
     * <br> - Количество продукта <= 0.
     */
    @Test
    public void testUpdateQuantityFail() {

        assertFalse(cartService.updateQuantity(null,null, null));

        assertFalse(cartService.updateQuantity(null, 1, CUSTOMER_ID1));

        assertFalse(cartService.updateQuantity(new Product(), 1, CUSTOMER_ID2));

        assertFalse(cartService.updateQuantity(new Product(), 0, CUSTOMER_ID1));

        assertFalse(cartService.updateQuantity(new Product(), -1, CUSTOMER_ID1));

        assertFalse(cartService.updateQuantity(new Product(), 10, CUSTOMER_ID1));

    }

    /**
     * Проверка успешного метода updateQuantity:
     * <br> - Обновление количества продукта в корзине.
     */
    @Test
    public void testUpdateQuantitySuccess() {

        assertTrue(cartService.updateQuantity(product, 10, CUSTOMER_ID1));

        Optional<OrderLine> orderLine1 = cart.getOrderLines().stream()
                .filter(value -> value.getProduct().equals(product))
                .findFirst();
        assertEquals(10, orderLine1.get().getProductQuantity());

    }

    /**
     * Проверка провального метода remove:
     * <br> - Входные параметры null.
     * <br> - Несуществующий пользователь.
     * <br> - Продукта нет в корзине.
     */
    @Test
    public void remove() {

        assertFalse(cartService.remove(null, null));

        assertFalse(cartService.remove(null, CUSTOMER_ID1));

        assertFalse(cartService.remove(new Product(), CUSTOMER_ID2));

        assertFalse(cartService.remove(new Product(), CUSTOMER_ID1));

    }

    /**
     * Проверка успешного метода remove:
     * <br> - Удаление продукта из корзины.
     */
    @Test
    public void testRemoveSuccess() {

        assertTrue(cartService.remove(product, CUSTOMER_ID1));

        Optional<OrderLine> orderLine1 = cart.getOrderLines().stream()
                .filter(value -> value.getProduct().equals(product))
                .findFirst();
        assertFalse(orderLine1.isPresent());

    }
}
