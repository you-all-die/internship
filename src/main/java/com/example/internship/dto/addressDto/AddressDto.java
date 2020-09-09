package com.example.internship.dto.addressDto;

import lombok.Data;

/**
 * @author Роман Каравашкин
 */

public enum AddressDto {
    ;

    private interface Id {
        Long getId();

        void setId(Long id);
    }

    private interface CustomerId {
        Long getCustomerId();

        void setCustomerId(Long CustomerId);
    }

    private interface Region {
        String getRegion();

        void setRegion(String region);
    }

    private interface City {
        String getCity();

        void setCity(String city);
    }

    private interface District {
        String getDistrict();

        void setDistrict(String district);
    }

    private interface Street {
        String getStreet();

        void setStreet(String street);
    }


    private interface House {
        String getHouse();

        void setHouse(String house);
    }


    private interface Apartment {
        String getApartment();

        void setApartment(String apartment);
    }

    private interface Comment {
        String getComment();

        void setComment(String comment);
    }

    public enum Request {
        ;

        @Data
        public static class Full implements Id, CustomerId, Region, City, District,
                Street, House, Apartment, Comment {
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
    }

    public enum Response {
        ;

        @Data
        public static class Full implements Id, CustomerId, Region, City, District,
                Street, House, Apartment, Comment {
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
    }
}
