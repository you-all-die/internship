package com.example.internship.dto.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum ProductDto {
    ;

    private interface Id {
        long getId();
    }

    private interface Name {
        String getName();
    }

    private interface Description {
        String getDescription();
    }

    private interface Picture {
        String getPicture();
    }

    private interface Price {
        BigDecimal getPrice();
    }

    private interface CategoryId {
        long getCategoryId();
    }

    public enum Request {
        ;

        @Data
        public static class All implements Id, Name, Description, Picture, Price {
            long id;
            String name;
            String description;
            String picture;
            BigDecimal price;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithCategoryId extends All implements CategoryId {
            long categoryId;
        }
    }

    public enum Response {
        ;

        @Data
        public static class Ids implements Id {
            private long id;
        }

        @Data
        public static class All implements Id, Name, Description, Picture, Price {
            private long id;
            private String name;
            private String description;
            private String picture;
            private BigDecimal price;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithCategoryId extends All implements CategoryId {
            private long categoryId;
        }

        @Data
        public static class SearchResult {
            private List<AllWithCategoryId> products;
            private Integer pageNumber;
            private Integer pageSize;
            private Long total;
        }
    }
}
