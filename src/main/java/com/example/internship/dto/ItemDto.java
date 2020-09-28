package com.example.internship.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author Sergey Lapshin
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private Integer itemQuantity;
    private Long itemCategoryId;
    private String itemDescription;
    private String itemName;
    private String itemPicture;
    private BigDecimal itemPrice;

}
