package com.example.internship.dto;

import com.example.internship.entity.Address;
import com.example.internship.entity.Cart;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class CustomerDto {
    private long id;
    @Size(min = 0, max = 64)
    private String firstName;
    @Size(min = 0, max = 64)
    private String middleName;
    @Size(min = 0, max = 64)
    private String lastName;
    @NotEmpty
    private String password;
    @Size(min = 0, max = 64)
    private String phone;
    @Size(min = 0, max = 64)
    @NotEmpty
    private String email;
    private Set<Address> addresses;
    private Cart cart;
}
