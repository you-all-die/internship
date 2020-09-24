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
    List<CategoryDto.Response.AllWithSubcategories> topCategories;
    /* Родительские категории для хлебных крошек */
    List<CategoryDto.Response.All> breadcrumbs;
    /* Номер текущей страницы */
    Integer pageNumber;
    /* Количество страницы */
    Integer totalPages;
    /* Размер страницы */
    Integer pageSize;
    /* Общее количество товаров в запросе */
    Long totalProducts;
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
}