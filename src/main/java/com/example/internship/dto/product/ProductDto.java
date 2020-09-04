package com.example.internship.dto.product;

import lombok.Data;

import java.math.BigDecimal;

public enum ProductDto {
    ;

    private interface Id {
        long getId();
        void setId(long id);
    }

    private interface Name {
        String getName();
        void setName(String name);
    }

    private interface Description {
        String getDescription();
        void setDescription(String description);
    }

    private interface Picture {
        String getPicture();
        void setPicture(String picture);
    }

    private interface Price {
        BigDecimal getPrice();
        void setPrice(BigDecimal price);
    }

    public enum Response {
        ;

        @Data
        public static class All implements Id, Name, Description, Picture, Price {
            private long id;
            private String name;
            private String description;
            private String picture;
            private BigDecimal price;
        }
    }
}
