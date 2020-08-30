package com.example.internship.dto.about;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum OutletDto {
    ;

    private interface Id {
        Long getId();
        void setId(Long id);
    }

    private interface City {
        String getCity();
        void setCity(String city);
    }

    private interface Name {
        String getName();
        void setName(String name);
    }

    private interface OpeningHours {
        String getOpeningHours();
        void setOpeningHours(String openingHours);
    }

    private interface Coordinates {
        Double getLongitude();
        void setLongitude(Double longitude);
        Double getLatitude();
        void setLatitude(Double latitude);
    }

    public enum Request {
        ;
    }

    public enum Response {
        ;

        @Data
        public static class Full implements Id, City, Name, OpeningHours, Coordinates {
            Long id;
            String city;
            String name;
            String openingHours;
            Double longitude;
            Double latitude;
        }

        @Data
        public static class OnlyCoordinates implements Coordinates {
            Double longitude;
            Double latitude;
        }
    }
}
