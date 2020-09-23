package com.example.internship.service.impl;

import com.example.internship.entity.Cart;
import com.example.internship.entity.Customer;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;
import com.example.internship.repository.CartRepository;
import com.example.internship.repository.CustomerRepository;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.cart.CartService;
import com.example.internship.service.cart.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Modenov D.A
 */
class CartServiceImplTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);

    private final ProductRepository productRepository = mock(ProductRepository.class);

    private final CartRepository cartRepository = mock(CartRepository.class);

    private final ModelMapper mapper = mock(ModelMapper.class);

    private CartService cartService;

    private final Long CUSTOMER_ID1 = 1L;
    private final Long CUSTOMER_ID2 = 2L;

    private final Long ORDER_LINE_ID1 = 1L;

    Customer customer;

    Cart cart;

    Product product;

    @BeforeEach
    public void before() {
        product = new Product();
        product.setId(1L);

        cart = new Cart();
        cart.setId(1L);
        cart.setOrderLines(new ArrayList<>() {{
            add(new OrderLine(ORDER_LINE_ID1, cart, product, 1));
        }});

        customer = new Customer();
        customer.setId(CUSTOMER_ID1);
        customer.setCart(cart);

        when(customerRepository.findById(eq(CUSTOMER_ID1))).thenReturn(Optional.of(customer));
        when(customerRepository.findById(eq(CUSTOMER_ID2))).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);
        cartService = new CartServiceImpl(customerRepository, productRepository, cartRepository, mapper);
    }

    /**
     * Проверка провального метода add:
     * <br> - Все аргументы null.
     */
    @Test
    public void testAddEmptyAllArgs() {
        assertFalse(cartService.add(any(Product.class), null));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода add:
     * <br> - Продукт равен null.
     */
    @Test
    public void testAddEmptyProduct() {
        assertFalse(cartService.add(any(Product.class), CUSTOMER_ID1));
        verify(cartRepository, never()).save(any());
    }

    /**
     * Проверка провального метода add:
     * <br> - Несуществующий пользователь.
     */
    @Test
    public void testAddEmptyCustomer() {
        assertFalse(cartService.add(new Product(), CUSTOMER_ID2));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка успешного выполнения метода add:
     * <br> - Добавление нового продукта в корзину.
     */
    @Test
    public void testAddFirstProduct() {
        Product testNewProduct = new Product();

        assertTrue(cartService.add(testNewProduct, CUSTOMER_ID1));

        Optional<OrderLine> orderLine1 = cart.getOrderLines().stream()
                .filter(value -> value.getProduct().equals(testNewProduct))
                .findFirst();
        assertEquals(testNewProduct, orderLine1.get().getProduct());

        verify(cartRepository, times(1)).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка успешного выполнения метода add:
     * <br> - Добавление существующего продукта в корзину.
     */
    @Test
    public void testAddExistingProduct() {
        Optional<OrderLine> orderLine1 = cart.getOrderLines().stream()
                .filter(value -> value.getProduct().equals(product))
                .findFirst();
        assertEquals(product, orderLine1.get().getProduct());

        assertTrue(cartService.add(product, CUSTOMER_ID1));

        assertEquals(2, orderLine1.get().getProductQuantity());

        verify(cartRepository, times(1)).save(any());
        verify(customerRepository, times(1)).findById(any());
    }


    /**
     * Проверка провального метода updateQuantity:
     * <br> - Входные аргументы null.
     */
    @Test
    public void testUpdateQuantityEmptyAllArgs() {
        assertFalse(cartService.updateQuantity(any(Product.class), null, null));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода updateQuantity:
     * <br> - Продукт равен null.
     */
    @Test
    public void testUpdateQuantityEmptyProduct() {
        assertFalse(cartService.updateQuantity(any(Product.class), 1, CUSTOMER_ID1));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода updateQuantity:
     * <br> - Не существующий пользователь.
     */
    @Test
    public void testUpdateQuantityEmptyCustomer() {
        assertFalse(cartService.updateQuantity(new Product(), 1, CUSTOMER_ID2));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода updateQuantity:
     * <br> - Количество продукта равно 0.
     */
    @Test
    public void testUpdateQuantityZeroQuantity() {
        assertFalse(cartService.updateQuantity(new Product(), 0, CUSTOMER_ID1));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода updateQuantity:
     * <br> - Количество продукта отрицательно.
     */
    @Test
    public void testUpdateQuantityNegativeQuantity() {
        assertFalse(cartService.updateQuantity(new Product(), -10, CUSTOMER_ID1));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода updateQuantity:
     * <br> - Продукта нет в корзине.
     */
    @Test
    public void testUpdateQuantityNoProductInCart() {
        assertFalse(cartService.updateQuantity(new Product(), 10, CUSTOMER_ID1));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка успешного метода updateQuantity:
     * <br> - Обновление количества продукта в корзине.
     */
    @Test
    public void testUpdateQuantitySuccess() {
        product.setPrice(BigDecimal.valueOf(100));

        assertTrue(cartService.updateQuantity(product, 10, CUSTOMER_ID1));

        Optional<OrderLine> orderLine1 = cart.getOrderLines().stream()
                .filter(value -> value.getProduct().equals(product))
                .findFirst();
        assertEquals(10, orderLine1.get().getProductQuantity());

        verify(cartRepository, times(1)).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода remove:
     * <br> - Входные аргументы null.
     */
    @Test
    public void testRemoveEmptyAllArgs() {
        assertFalse(cartService.remove(any(Product.class), null));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода remove:
     * <br> - Продукт равен null.
     */
    @Test
    public void testRemoveEmptyProduct() {
        assertFalse(cartService.remove(any(Product.class), CUSTOMER_ID1));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода remove:
     * <br> - Не существующий пользователь.
     */
    @Test
    public void testRemoveEmptyCustomer() {
        assertFalse(cartService.remove(new Product(), CUSTOMER_ID2));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода remove:
     * <br> - Продукта нет в корзине.
     */
    @Test
    public void testRemoveNoProductInCart() {
        assertFalse(cartService.remove(new Product(), CUSTOMER_ID1));

        verify(cartRepository, never()).save(any());
        verify(customerRepository, times(1)).findById(any());
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

        verify(cartRepository, times(1)).save(any());
        verify(customerRepository, times(1)).findById(any());
    }


    /**
     * Проверка провального метода getTotalPrice:
     * <br> - Входные аргументы null.
     */
    @Test
    public void testGetTotalPriceEmptyArgs() {
        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(null));

        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода getTotalPrice:
     * <br> - Несуществующий пользователь.
     */
    @Test
    public void testGetTotalPriceEmptyCustomer() {
        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(CUSTOMER_ID2));

        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка провального метода getTotalPrice:
     * <br> - Нет товаров в корзине.
     */
    @Test
    public void testGetTotalPriceNoProductInCart() {
        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(CUSTOMER_ID1));

        verify(customerRepository, times(1)).findById(any());
    }

    /**
     * Проверка успешного метода getTotalPrice:
     * <br> - Подсчет цены товаров в корзине.
     */
    @Test
    public void testGetTotalPriceSuccess() {
        Product newProduct = new Product();
        newProduct.setPrice(BigDecimal.valueOf(999.99));

        OrderLine orderLine2 = new OrderLine();

        orderLine2.setProduct(newProduct);
        orderLine2.setProductQuantity(2);
        cart.getOrderLines().add(orderLine2);

        assertEquals(BigDecimal.valueOf(1999.98), cartService.getTotalPrice(CUSTOMER_ID1));
        verify(customerRepository, times(1)).findById(any());
    }
}
