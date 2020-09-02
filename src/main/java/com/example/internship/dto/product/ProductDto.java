package com.example.internship.dto.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Мурашов Алексей
 */
public enum ProductDto {
    ;

    private interface Id {
        Long getId();
        void setId(Long id);
    }

    private interface CategoryId {
        Long getCategoryId();
        void setCategoryId(Long categoryId);
    }

    private interface CategoryName {
        String getCategoryName();
        void setCategoryName(String categoryName);
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

    private interface StatusId {
        Long getStatusId();
        void setStatusId(Long statusId);
    }

    private interface StatusDescription {
        String getStatusDescription();
        void setStatusDescription(String statusDescription);
    }

    public enum Request {
        ;


    }

    public enum Response {
        ;

        @Data
        public static class Full implements Id, Name, CategoryId, CategoryName, Description, Picture, Price, StatusId, StatusDescription  {
            private Long id;
            private String name;
            private Long categoryId;
            private String categoryName;
            private String description;
            private String picture;
            private BigDecimal price;
            private Long statusId;
            private String statusDescription;
        }
    }
}
