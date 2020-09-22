package com.example.internship.controller.checkout;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class CheckoutForm {
    @NotEmpty
    private String firstName;

    private String middleName;

    @NotEmpty
    private String lastName;

    @Digits(integer = 11, fraction = 0)
    private String phone;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String region;

    @NotEmpty
    private String city;

    private String district;

    @NotEmpty
    private String street;

    @NotEmpty
    private String house;

    @NotEmpty
    private String apartment;

    private String comment;
}
