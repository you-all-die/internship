package com.example.internship.dto.outlet;

import lombok.Data;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum OutletDto {
    ;

    private interface Id {
        long getId();
        void setId(long id);
    }

    private interface City {
        String getCity();
        void setCity(String city);
    }

    private interface Name {
        String getName();
        void setName(String name);
    }

    private interface AddressAndPhone {
        String getAddress();
        void setAddress(String address);
        String getPhone();
        void setPhone(String phone);
    }

    private interface OpeningHours {
        String getOpeningHours();
        void setOpeningHours(String openingHours);
    }

    private interface Coordinates {
        double getLongitude();
        void setLongitude(double longitude);
        double getLatitude();
        void setLatitude(double latitude);
    }

    public enum Response {
        ;

        @Data
        public static class Full implements Id, City, Name, AddressAndPhone, OpeningHours, Coordinates {
            private long id;
            private String city;
            private String name;
            private String address;
            private String phone;
            private String openingHours;
            private double longitude;
            private double latitude;
        }

        @Data
        public static class OnlyCoordinates implements Coordinates {
            private double longitude;
            private double latitude;
        }
    }
}
