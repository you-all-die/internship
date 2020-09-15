package com.example.internship.dto.addressDto;

import lombok.*;

/**
 * @author Роман Каравашкин
 */
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long id;
    private Long customerId;
    private String region;
    private String city;
    private String district;
    private String street;
    private String house;
    private String apartment;
    private String comment;
}
