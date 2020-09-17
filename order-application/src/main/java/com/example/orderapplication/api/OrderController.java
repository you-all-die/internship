package com.example.orderapplication.api;

import com.example.orderapplication.dto.OrderDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Modenov D.A
 */
@RestController
@RequestMapping("api/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    @GetMapping
    @ApiOperation(value = "Возвращает заказ по id заказа и источнику заказа.")
    @Schema(implementation = OrderDto.class)
    public ResponseEntity<OrderDto> getOrder(@RequestParam(name = "id")
                                                 @ApiParam(value = "id заказа") Long orderId,
                                             @RequestParam(name = "source")
                                                 @ApiParam(value = "Источник заказа") String source) {

        log.info("Сработал orderId при get запросе - " + orderId);
        log.info("Сработал source при get запросе - " + source);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    @ApiOperation(value = "Создает новый заказ")
    @Schema(implementation = OrderDto.class)
    public ResponseEntity<OrderDto> add(@Valid @RequestBody @ApiParam(value = "Заказ") OrderDto order) {

        return ResponseEntity.ok(order);
    }
}
