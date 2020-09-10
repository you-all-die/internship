package com.example.internship.api;

import com.example.internship.dto.OrderLineForm;
import com.example.internship.dto.OrderLineDto;
import com.example.internship.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        return orderLines.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(orderLines, HttpStatus.OK);
    }

    @PutMapping("{customerId}/add/{productId}")
    @ApiOperation(value = "Добавляет товар в корзину.")
    public ResponseEntity<?> add(@PathVariable Long customerId, @PathVariable Long productId) {

        return cartService.add(productId, customerId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("{customerId}")
    @ApiOperation(value = "Обновляет количество товара в корзине.")
    public ResponseEntity<?> updateQuantity(@PathVariable Long customerId, @RequestBody OrderLineForm orderLineForm) {

        return cartService.updateQuantity(orderLineForm.getProductId(),
                orderLineForm.getProductQuantity(), customerId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{customerId}/delete/{productId}")
    @ApiOperation(value = "Удаляет один товар из корзины.")
    public ResponseEntity<?> remove(@PathVariable Long customerId, @PathVariable Long productId) {

        return cartService.remove(productId, customerId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{customerId}")
    @ApiOperation(value = "Удаляет все товары в корзине.")
    public ResponseEntity<?> removeAll(@PathVariable Long customerId) {

        return cartService.removeAll(customerId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
