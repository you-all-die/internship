package com.example.internship.dto.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum ProductDto {
    ;

    private interface Id {
        Long getId();
    }

    private interface Name {
        String getName();
    }

    private interface Description {
        String getDescription();
    }

    private interface Extension {
        String getExtension();
    }

    private interface Price {
        BigDecimal getPrice();
    }

    private interface CategoryId {
        Long getCategoryId();
    }

    public enum Request {
        ;

        @Data
        public static class All implements Id, Name, Description, Extension, Price {
            Long id;
            String name;
            String description;
            String extension;
            BigDecimal price;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithCategoryId extends All implements CategoryId {
            Long categoryId;
        }
    }

    public enum Response {
        ;

        @Data
        public static class Ids implements Id {
            private Long id;
        }

        @Data
        public static class All implements Id, Name, Description, Extension, Price {
            private Long id;
            private String name;
            private String description;
            private String extension;
            private BigDecimal price;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithCategoryId extends All implements CategoryId {
            private Long categoryId;
        }

    }
}
