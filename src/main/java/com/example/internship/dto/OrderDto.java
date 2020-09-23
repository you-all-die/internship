package com.example.internship.dto;

import lombok.*;

import java.math.BigDecimal;
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

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (ItemDto item : items) {
            totalPrice = totalPrice.add(item.getItemPrice().multiply(new BigDecimal(item.getItemQuantity())));
        }
        return totalPrice;
    }

    public OrderDto(long l, String firstName, String middleName, String lastName, String s, String region, String city, String district, String street, String house, String apartmant, String comment, String status, List<ItemDto> itemDtos) {
    }
}
