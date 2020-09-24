package com.example.internship.service.cart;

import com.example.internship.dto.OrderLineDto;
import com.example.internship.entity.OrderLine;
import com.example.internship.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Modenov D.A
 */
public interface CartService {

    /**
     * Добавляет товар в корзину.
     * @param productId id товара.
     * @param customerId id пользователя.
     * @return true если товар добавлен и false если нет.
     */
    boolean add(Long productId, Long customerId);

    /**
     * Добавляет товар в корзину.
     * @param product товар.
     * @param customerId id пользователя.
     * @return true если товар добавлен и false если нет.
     * 
     * @deprecated 
     * <br> Используйте метод {@link #add(Long productId, Long customerId)}
     */
    @Deprecated
    boolean add(Product product, Long customerId);

    /**
     * Обновляет количество товара в корзине.
     * @param productId id товара.
     * @param productQuantity новое количество товара (не может быть >1).
     * @param customerId id пользователя.
     * @return true если количество товара обновлено и false если нет.
     */
    boolean updateQuantity(Long productId, Integer productQuantity, Long customerId);

    /**
     * Обновляет количество товара в корзине.
     * @param product товар.
     * @param productQuantity новое количество товара (не может быть >1).
     * @param customerId id пользователя.
     * @return true если количество товара обновлено и false если нет.
     *
     * @deprecated
     * <br> Используйте метод
     * {@link #updateQuantity(Long productId, Integer productQuantity, Long customerId)}
     */
    @Deprecated
    boolean updateQuantity(Product product, Integer productQuantity, Long customerId);

    /**
     * Удаляет товар из корзины.
     * @param productId id товара.
     * @param customerId id пользователя.
     * @return true если товар удален из корзины и false если нет.
     */
    boolean remove(Long productId, Long customerId);

    /**
     * Удаляет товар из корзины.
     * @param product товар.
     * @param customerId id пользователя.
     * @return true если товар удален из корзины и false если нет.
     *
     * @deprecated
     * <br> Используйте метод
     * {@link #remove(Long productId, Long customerId)}
     */
    @Deprecated
    boolean remove(Product product, Long customerId);

    /**
     * Удаляет все товары из корзины.
     * @param customerId id пользователя.
     * @return true если все товары удалены и false если нет.
     */
    boolean removeAll(Long customerId);

    /**
     * Возвращает все линии заказов из корзины.
     * @param customerId id пользователя.
     * @return List линий заказов или null.
     */
    List<OrderLineDto> findAll(Long customerId);

    /**
     * Общая сумма всех товаров в корзине
     * @param customerId id пользователя.
     * @return сумма всех товаров.
     */
    BigDecimal getTotalPrice(Long customerId);
}
