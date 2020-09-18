package com.example.internship.controller.checkout;

import lombok.Data;

@Data
public class CheckoutForm {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String region;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String district;
    private String comment;
}
