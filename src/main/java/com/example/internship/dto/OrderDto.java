package com.example.internship.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sergey Lapshin
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;
    private String addressRegion;
    private String addressCity;
    private String addressDistrict;
    private String addressStreet;
    private String addressHouse;
    private String addressApartment;
    private String addressComment;
    private String status;
    private List<ItemDto> items;
    private Timestamp date;

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ItemDto item : items) {
            totalPrice = totalPrice.add(item.getItemPrice().multiply(new BigDecimal(item.getItemQuantity())));
        }
        return totalPrice;
    }

}
