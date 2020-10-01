package com.example.internship.api;

import com.example.internship.dto.OrderDto;
import com.example.internship.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    @Operation(description = "Получение информации по заказу")
    @Schema(implementation = OrderDto.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    public ResponseEntity<OrderDto> getOrderInfo(@Parameter(description = "Идентификатор заказа") @PathVariable Long id) {
        OrderDto order = orderService.findById(id);
        return order == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);
    }

    @PostMapping("/customer/{id}")
    @Operation(description = "Добавление заказа пользователю")
    @Schema(implementation = OrderDto.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ добавлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Неправильные данные")
    })
    @Parameters(value = {
            @Parameter(required = true, name = "id", description = "Идентификатор пользователя"),
            @Parameter(name = "orderDto", description = "Данные заказа", required = true)
    })
    public ResponseEntity<?> addOrder(@PathVariable("id") Long customerId, @Valid @RequestBody OrderDto orderDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(e -> errors.append(e.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        OrderDto newOrder = orderService.addOrderToCustomer(customerId, orderDto);

        return newOrder != null ? ResponseEntity.ok(orderDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{id}")
    @Operation(description = "Получение заказов пользователя")
    @Schema(implementation = List.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказы найдены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Идентификатор пользователя", required = true),
            @Parameter(name = "pageNumber", description = "Номер страницы"),
            @Parameter(name = "pageSize", description = "Размер страницы")
    })
    public ResponseEntity<?> getCustomerOrders(@PathVariable("id") Long customerId,
                                               @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                               @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize) {

        List<OrderDto> orders = orderService.getCustomerOrders(customerId, PageRequest.of(pageNumber, pageSize));

        return orders != null ? ResponseEntity.ok(orders) : ResponseEntity.notFound().build();
    }
}
