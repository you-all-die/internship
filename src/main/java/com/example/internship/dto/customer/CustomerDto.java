package com.example.internship.dto.customer;

import lombok.Value;

public enum CustomerDto {
    ;

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

    public enum Response {
        ;

        /**
         * ОПД с основными полями
         */
        @Value
        public static class AllExceptPassword implements Id, Names, Phone, Email {
            Long id;
            String firstName;
            String middleName;
            String lastName;
            String phone;
            String email;
        }

        /**
         * ОПД для вывода списка покупателей
         */
        @Value
        public static class WithFullName implements Id, FullName, Phone, Email {
            Long id;
            String fullName;
            String phone;
            String email;
        }
    }
}
