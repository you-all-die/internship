package com.example.internship.dto.address;

import lombok.Data;

public enum AddressDto {
    ;

    private interface CustomerId {
        Long getCustomerId();
    }

    @Data
    public static class ForEditor implements Id, CustomerId, Comment {
        private Long id;
        private Long customerId;
        private String comment;
    }

    private interface Id {
        Long getId();
    }

    private interface Customer {
        Customer getCustomer();
    }

    @Data
    public static class ForList implements Id, FullAddress, Comment {
        private Long id;
        private String fullAddress;
        private String Comment;
    }

    private interface Region {
        String getRegion();
    }

    private interface City {
        String getCity();
    }

    private interface District {
        String getDistrict();
    }

    private interface Street {
        String getStreet();
    }

    private interface House {
        String getHouse();
    }

    private interface Apartment {
        String getApartment();
    }

    private interface Comment {
        String getComment();
    }

    private interface FullAddress {
        String getFullAddress();
    }
}
