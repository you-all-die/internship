package com.example.internship.dto.outlet;

import lombok.Data;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum OutletDto {
    ;

    private interface AllButCoordinates {
        long getId();
        void setId(long id);
        String getCity();
        void setCity(String city);
        String getName();
        void setName(String name);
        String getAddress();
        void setAddress(String address);
        String getPhone();
        void setPhone(String phone);
        String getOpeningHours();
        void setOpeningHours(String openingHours);
    }

    private interface CoordinatesOnly {
        double getLongitude();
        void setLongitude(double longitude);
        double getLatitude();
        void setLatitude(double latitude);
    }

    public enum Response {
        ;

        @Data
        public static class Full implements AllButCoordinates, CoordinatesOnly {
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
        public static class Coordinates implements CoordinatesOnly {
            private double longitude;
            private double latitude;
        }
    }
}
