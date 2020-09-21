package com.example.internship.dto.address;

public enum AddressDto {
    ;

    public enum Response {
        ;

    }

    private interface Id {
        Long getId();
    }

    private interface Customer {
        Customer getCustomer();
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
}
