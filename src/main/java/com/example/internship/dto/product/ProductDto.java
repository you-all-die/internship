package com.example.internship.dto.product;

import com.example.internship.dto.category.CategoryDto;
import lombok.*;

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

        @Value
        public static class SearchResult {
            List<AllWithCategoryId> products;
            List<CategoryDto.Response.All> topCategories;
            Integer pageNumber;
            Integer pageSize;
            Long total;
            BigDecimal lowerPriceLimit;
            BigDecimal upperPriceLimit;

            private SearchResult(Builder builder) {
                assert null != builder.products;
                assert null != builder.topCategories;
                assert null != builder.pageNumber;
                assert null != builder.pageSize;
                assert null != builder.total;

                products = builder.products;
                topCategories = builder.topCategories;
                pageNumber = builder.pageNumber;
                pageSize = builder.pageSize;
                total = builder.total;
                lowerPriceLimit = null != builder.lowerPriceLimit ? builder.lowerPriceLimit : BigDecimal.ZERO;
                upperPriceLimit = null != builder.upperPriceLimit ? builder.upperPriceLimit : BigDecimal.ZERO;
            }

            public static class Builder {
                private List<AllWithCategoryId> products;
                private List<CategoryDto.Response.All> topCategories;
                private Integer pageNumber;
                private Integer pageSize;
                private Long total;
                private BigDecimal lowerPriceLimit;
                private BigDecimal upperPriceLimit;

                public Builder() {
                }

                public Builder products(List<AllWithCategoryId> products) {
                    this.products = products;
                    return this;
                }

                public Builder topCategories(List<CategoryDto.Response.All> topCategories) {
                    this.topCategories = topCategories;
                    return this;
                }

                public Builder lowerPriceLimit(BigDecimal lowerPriceLimit) {
                    this.lowerPriceLimit = lowerPriceLimit;
                    return this;
                }

                public Builder upperPriceLimit(BigDecimal upperPriceLimit) {
                    this.upperPriceLimit = upperPriceLimit;
                    return this;
                }

                public Builder pageNumber(Integer pageNumber) {
                    this.pageNumber = pageNumber;
                    return this;
                }

                public Builder pageSize(Integer pageSize) {
                    this.pageSize = pageSize;
                    return this;
                }

                public Builder total(Long total) {
                    this.total = total;
                    return this;
                }

                public SearchResult build() {
                    return new SearchResult(this);
                }
            }
        }
    }
}
