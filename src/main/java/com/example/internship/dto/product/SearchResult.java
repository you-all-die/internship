package com.example.internship.dto.product;

import com.example.internship.dto.category.CategoryDto;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

/**
 * Данные для заполнения страницы каталога в шаблоне /product/index.html
 */
@Value
public class SearchResult {
    /* Список продуктов на страницу */
    List<ProductDto.Response.AllWithCategoryId> products;
    /* Категории высшего уровня */
    List<CategoryDto.Response.AllWithParentSubcategories> topCategories;
    /* Номер текущей страницы */
    Integer pageNumber;
    /* Размер страницы */
    Integer pageSize;
    /* Общее количество товаров в запросе */
    Long total;
    /* Нижняя граница цены товара */
    BigDecimal lowerPriceLimit;
    /* Верхняя граница цены товара */
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
        private List<ProductDto.Response.AllWithCategoryId> products;
        private List<CategoryDto.Response.AllWithParentSubcategories> topCategories;
        private Integer pageNumber;
        private Integer pageSize;
        private Long total;
        private BigDecimal lowerPriceLimit;
        private BigDecimal upperPriceLimit;

        public Builder() {
        }

        public Builder products(List<ProductDto.Response.AllWithCategoryId> products) {
            this.products = products;
            return this;
        }

        public Builder topCategories(List<CategoryDto.Response.AllWithParentSubcategories> topCategories) {
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