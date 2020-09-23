package com.example.internship.api;

import com.example.internship.api.dto.OrderLineForm;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Modenov D.A
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartRestController {

    private final CartService cartService;

    @GetMapping("{customerId}")
    @Operation(summary = "Возвращает список всех линий заказа в корзине.")
    public ResponseEntity<List<OrderLineDto>> findAll(@PathVariable("customerId") Long customerId) {
        List<OrderLineDto> orderLines = cartService.findAll(customerId);

        return orderLines.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(orderLines);
    }

    @PutMapping("{customerId}/add/{productId}")
    @Operation(summary = "Добавляет товар в корзину.")
    public ResponseEntity<?> add(@PathVariable Long customerId, @PathVariable Long productId) {

        return cartService.add(productId, customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("{customerId}")
    @Operation(summary = "Обновляет количество товара в корзине.")
    public ResponseEntity<?> updateQuantity(@PathVariable @Parameter(description = "id пользователя.") Long customerId,
                                            @RequestBody @Parameter(description = "Форма заполнения продукта и его количества.") OrderLineForm orderLineForm) {

        return cartService.updateQuantity(orderLineForm.getProductId(),
                orderLineForm.getProductQuantity(), customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{customerId}/delete/{productId}")
    @Operation(summary = "Удаляет один товар из корзины.")
    public ResponseEntity<?> remove(@PathVariable Long customerId, @PathVariable Long productId) {

        return cartService.remove(productId, customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{customerId}")
    @Operation(summary = "Удаляет все товары в корзине.")
    public ResponseEntity<?> removeAll(@PathVariable Long customerId) {

        return cartService.removeAll(customerId) ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
