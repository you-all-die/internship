package com.example.internship.dto.customer;

import com.example.internship.dto.address.AddressDto;
import lombok.Data;

import java.util.List;

public enum CustomerDto {
    ;

    private interface Addresses {
        List<AddressDto.ForList> getAddresses();
    }

    /**
     * ОПД с основными полями
     */
    @Data
    public static class AllExceptPassword implements Id, Names, Phone, Email {
        Long id;
        String firstName;
        String middleName;
        String lastName;
        String phone;
        String email;
    }

    private interface Id {
        Long getId();
    }

    private interface Names {
        String getFirstName();
        String getMiddleName();
        String getLastName();
    }

    private interface Phone {
        String getPhone();
    }

    private interface Email {
        String getEmail();
    }

    private interface FullName {
        String getFullName();
    }

    /**
     * ОПД для вывода списка покупателей
     */
    @Data
    public static class WithFullName implements Id, Names, FullName, Phone, Email, Addresses {
        Long id;
        String firstName;
        String middleName;
        String lastName;
        String fullName;
        String phone;
        String email;
        List<AddressDto.ForList> addresses;
    }
}
