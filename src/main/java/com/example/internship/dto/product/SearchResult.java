package com.example.internship.dto.product;

import com.example.internship.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

/**
 * Данные для заполнения страницы каталога в шаблоне /product/index.html
 */
@Value
@Builder
public class SearchResult {
    /* Текущая категория (null для всех) */
    Long categoryId;
    /* Список продуктов на страницу */
    List<ProductDto.Response.AllWithCategoryId> products;
    /* Категории высшего уровня */
    List<CategoryDto.Response.AllWithParentSubcategories> topCategories;
    /* Родительские категории для хлебных крошек */
    List<CategoryDto.Response.All> breadcrumbs;
    /* Страницы */
    boolean[] pages;
    /* Номер текущей страницы */
    Integer pageNumber;
    /* Размер страницы */
    Integer pageSize;
    /* Общее количество товаров в запросе */
    Long total;
    /* Минимальная цена товара */
    BigDecimal minimalPrice;
    /* Максимальная цена товара */
    BigDecimal maximalPrice;
    /* Нижняя граница цены товара */
    BigDecimal lowerPriceLimit;
    /* Верхняя граница цены товара */
    BigDecimal upperPriceLimit;
    /* Порядок сортировки цен */
    Boolean descendingOrder;

/*
    private SearchResult(Builder builder) {
        assert null != builder.products;
        assert null != builder.topCategories;
        assert null != builder.breadcrumbs;
        assert null != builder.pages && builder.pages.length > 0;
        assert null != builder.pageNumber;
        assert null != builder.pageSize;
        assert null != builder.total;

        categoryId = builder.categoryId;
        products = builder.products;
        topCategories = builder.topCategories;
        breadcrumbs = builder.breadcrumbs;
        pages = builder.pages;
        pageNumber = builder.pageNumber;
        pageSize = builder.pageSize;
        total = builder.total;
        minimalPrice = builder.minimalPrice;
        maximalPrice = builder.maximalPrice;
        lowerPriceLimit = builder.lowerPriceLimit;
        upperPriceLimit = builder.upperPriceLimit;
        descendingOrder = builder.descendingOrder;
    }
*/

/*
    public static class Builder {
        private Long categoryId;
        private List<ProductDto.Response.AllWithCategoryId> products;
        private List<CategoryDto.Response.AllWithParentSubcategories> topCategories;
        private List<CategoryDto.Response.All> breadcrumbs;
        private boolean[] pages;
        private Integer pageNumber;
        private Integer pageSize;
        private Long total;
        private BigDecimal minimalPrice;
        private BigDecimal maximalPrice;
        private BigDecimal lowerPriceLimit;
        private BigDecimal upperPriceLimit;
        private Boolean descendingOrder;

        public Builder() {
        }

        public Builder categoryId(final Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder products(final List<ProductDto.Response.AllWithCategoryId> products) {
            this.products = products;
            return this;
        }

        public Builder topCategories(final List<CategoryDto.Response.AllWithParentSubcategories> topCategories) {
            this.topCategories = topCategories;
            return this;
        }

        public Builder breadcrumbs(final List<CategoryDto.Response.All> breadcrumbs) {
            this.breadcrumbs = breadcrumbs;
            return this;
        }

        public Builder pages(final boolean[] pages) {
            this.pages = pages;
            return this;
        }

        public Builder priceBounds(final BigDecimal minimalPrice, final BigDecimal maximalPrice) {
            this.minimalPrice = minimalPrice;
            this.maximalPrice = maximalPrice;
            return this;
        }

        public Builder priceLimits(final BigDecimal lowerPriceLimit, final BigDecimal upperPriceLimit) {
            this.lowerPriceLimit = lowerPriceLimit;
            this.upperPriceLimit = upperPriceLimit;
            return this;
        }

        public Builder pageNumber(final Integer pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder pageSize(final Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder total(final Long total) {
            this.total = total;
            return this;
        }

        public Builder descendingOrder(final Boolean descendingOrder) {
            this.descendingOrder = descendingOrder;
            return this;
        }

        public SearchResult build() {
            return new SearchResult(this);
        }
    }
*/
}