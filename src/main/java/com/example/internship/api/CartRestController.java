package com.example.internship.api;

import com.example.internship.api.dto.OrderLineUpdateRequest;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.service.cart.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<OrderLineDto>> findAll(@PathVariable("customerId") Long customerId) {
        List<OrderLineDto> orderLines = cartService.findAll(customerId);

        return orderLines.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(orderLines);
    }

    @PutMapping("{customerId}/add/{productId}")
    @ApiOperation(value = "Добавляет товар в корзину.")
    public ResponseEntity<?> add(@PathVariable Long customerId, @PathVariable Long productId) {

        return cartService.add(productId, customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("{customerId}")
    @ApiOperation(value = "Обновляет количество товара в корзине.")
    public ResponseEntity<?> updateQuantity(@PathVariable @ApiParam(value = "id пользователя.") Long customerId,
                                            @RequestBody @ApiParam(value = "Форма заполнения продукта и его количества.") OrderLineUpdateRequest orderLineUpdateRequest) {

        return cartService.updateQuantity(orderLineUpdateRequest.getProductId(),
                orderLineUpdateRequest.getProductQuantity(), customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{customerId}/delete/{productId}")
    @ApiOperation(value = "Удаляет один товар из корзины.")
    public ResponseEntity<?> remove(@PathVariable Long customerId, @PathVariable Long productId) {

        return cartService.remove(productId, customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{customerId}")
    @ApiOperation(value = "Удаляет все товары в корзине.")
    public ResponseEntity<?> removeAll(@PathVariable Long customerId) {

        return cartService.removeAll(customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
