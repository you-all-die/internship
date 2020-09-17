package com.example.internship.dto.customer;

import lombok.Data;
import lombok.Value;

public enum CustomerDto {
    ;

    private interface Id {
        Long getId();
        void setId(Long id);
    }

    private interface Names {
        String getFirstName();
        void setFirstName(String firstName);
        String getMiddleName();
        void setMiddleName(String middleName);
        String getLastName();
        void setLastName(String lastName);
    }

    private interface Phone {
        String getPhone();
        void setPhone(String phone);
    }

    private interface Email {
        String getEmail();
        void setEmail(String email);
    }

    private interface FullName {
        String getFullName();
        void setFullName(String fullName);
    }

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
}
