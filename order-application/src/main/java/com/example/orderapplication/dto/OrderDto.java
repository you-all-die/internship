package com.example.orderapplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Modenov D.A
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @Schema(description = "id заказа")
    @NotNull
    private Long orderId;

    @Schema(description = "Источник заказа")
    @NotNull
    private String source;

    @Schema(implementation = CustomerDto.class)
    @NotNull
    private CustomerDto customer;

    @Schema(description = "Адрес доставки или самовывоза")
    @NotNull
    private String address;

    @Schema(description = "Тип доставки")
    @NotNull
    private String deliveryType;

    @Schema(description = "Статус заказа")
    private String orderStatus;

    @Schema(implementation = OrderLineDto.class)
    List<OrderLineDto> orderLines;

}
