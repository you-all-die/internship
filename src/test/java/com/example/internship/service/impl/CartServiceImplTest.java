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

import java.math.BigDecimal;
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
    public void testRemoveFail() {

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

    /**
     * Проверка провального метода getTotalPrice:
     * <br> - Входные параметры null.
     * <br> - Несуществующий пользователь.
     * <br> - Нет товаров в корзине.
     */
    @Test
    public void testGetTotalPriceFail() {

        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(null));

        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(CUSTOMER_ID2));

        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(CUSTOMER_ID1));
    }

    /**
     * Проверка успешного метода getTotalPrice:
     * <br> - Подсчет цены одного товара в корзине.
     * <br> - Подсчет цены двух товаров в корзине.
     */
    @Test
    public void testGetTotalPriceSuccess() {

        OrderLine orderLine3 = new OrderLine();
        OrderLine orderLine4 = new OrderLine();

        Product product3 = new Product();
        product3.setPrice(BigDecimal.valueOf(100));

        Product product4 = new Product();
        product4.setPrice(BigDecimal.valueOf(999.99));


        orderLine3.setProduct(product3);
        orderLine3.setProductQuantity(1);
        cart.getOrderLines().add(orderLine3);

        assertEquals(BigDecimal.valueOf(100), cartService.getTotalPrice(CUSTOMER_ID1));


        orderLine4.setProduct(product4);
        orderLine4.setProductQuantity(2);
        cart.getOrderLines().add(orderLine4);

        assertEquals(BigDecimal.valueOf(2099.98), cartService.getTotalPrice(CUSTOMER_ID1));

    }
}
