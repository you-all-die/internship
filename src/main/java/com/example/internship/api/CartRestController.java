package com.example.internship.api;

import com.example.internship.dto.CartForm;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Modenov D.A
 */
@RestController
@RequestMapping("/api/cart")
@Api("cart")
@RequiredArgsConstructor
public class CartRestController {

    private final CartService cartService;

    @GetMapping("{customerId}")
    @ApiOperation(value = "Возвращает список всех линий заказа в корзине.")
    public List<OrderLineDto> findAll(@PathVariable("customerId") Long customerId) {
        return cartService.findAll(customerId);
    }

    @PutMapping("{customerId}/add/{productId}")
    @ApiOperation(value = "Добавляет товар в корзину.")
    public boolean add(@PathVariable Long customerId, @PathVariable Long productId) {
        return cartService.addFromApi(productId, customerId);
    }

    @PostMapping("{customerId}")
    @ApiOperation(value = "Обновляет количество товара в корзине.")
    public boolean updateQuantity(@PathVariable Long customerId, @RequestBody CartForm cartForm) {
        return cartService.updateQuantityFromApi(cartForm.getProductId(), cartForm.getProductQuantity(), customerId);
    }

    @DeleteMapping("{customerId}/delete/{productId}")
    @ApiOperation(value = "Удаляет один товар из корзины.")
    public boolean remove(@PathVariable Long customerId, @PathVariable Long productId) {
        return cartService.removeFromApi(productId, customerId);
    }

    @DeleteMapping("{customerId}")
    @ApiOperation(value = "Удаляет все товары в корзине.")
    public boolean removeAll(@PathVariable Long customerId) {
        return cartService.removeAll(customerId);
    }
}
