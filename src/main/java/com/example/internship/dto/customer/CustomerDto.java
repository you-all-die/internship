package com.example.internship.dto.customer;

import lombok.Data;

public enum CustomerDto {
    ;


    public enum Response {
        ;

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

        /**
         * ОПД для вывода списка покупателей
         */
        @Data
        public static class WithFullName implements Id, FullName, Phone, Email {
            Long id;
            String fullName;
            String phone;
            String email;
        }
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
}
