package com.example.internship.controller.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto.Response.SearchResult;
import com.example.internship.entity.Category;
import com.example.internship.entity.Product;
import com.example.internship.helper.WebHelper;
import com.example.internship.service.CartService;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class GsProductController {

    private static final String SEARCH_STRING_COOKIE = "productSearchString";
    private static final String CATEGORY_ID_COOKIE = "productCategoryId";
    private static final String LOWER_PRICE_COOKIE = "productLowerPriceLimit";
    private static final String UPPER_PRICE_COOKIE = "productUpperPriceLimit";
    private static final String MINIMAL_PRICE_COOKIE = "productMinimalPrice";
    private static final String MAXIMAL_PRICE_COOKIE = "productMaximalPrice";
    private static final String PAGE_NUMBER_COOKIE = "productPageNumber";
    private static final String PAGE_SIZE_COOKIE = "productPageSize";
    private static final String DESCENDING_COOKIE = "productDescendingOrder";

    private final CartService cartService;
    private final ProductService productService;
    private final GsProductService gsProductService;
    private final GsCategoryService categoryService;

    /**
     * @author Роман Каравашкин
     */
    @GetMapping("/{id}") // убрал ненужный /product (СЮА)
    public String showProduct(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/product";
    }

    @GetMapping
    public String viewProductPage(
    ) {
        return "product/index";
    }

    @PostMapping("/cart")
    public void addToCart(
            @RequestParam("productId") Product product,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
            cartService.add(product, Long.valueOf(customerId));
    }

    @GetMapping("/cards")
    public String viewCards(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @CookieValue(value = SEARCH_STRING_COOKIE, required = false) String searchString,
            @CookieValue(value = CATEGORY_ID_COOKIE, required = false) Long categoryId,
            @CookieValue(value = LOWER_PRICE_COOKIE, required = false) BigDecimal lowerPriceLimit,
            @CookieValue(value = UPPER_PRICE_COOKIE, required = false) BigDecimal upperPriceLimit,
            @CookieValue(value = PAGE_NUMBER_COOKIE, required = false, defaultValue = "0") Integer pageNumber,
            @CookieValue(value = PAGE_SIZE_COOKIE, required = false, defaultValue = "20") Integer pageSize,
            @CookieValue(value = DESCENDING_COOKIE, required = false) Boolean descendingOrder
    ) {
        if (!WebHelper.isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
        if (null == searchString) {
            searchString = "";
            response.addCookie(new Cookie(SEARCH_STRING_COOKIE, searchString));
        }
        final SearchResult searchResult = gsProductService.findByCriteria(
                searchString, categoryId, lowerPriceLimit, upperPriceLimit, pageNumber, pageSize, descendingOrder
        );
        model.addAttribute("products", searchResult.getProducts());
        return "/product/cards :: widget";
    }

    @GetMapping("/filter")
    public String viewFilter(
            HttpServletRequest request,
            @CookieValue(value = SEARCH_STRING_COOKIE, required = false) String searchString,
            @CookieValue(value = CATEGORY_ID_COOKIE, required = false) Category category,
            @CookieValue(value = LOWER_PRICE_COOKIE, required = false) BigDecimal lowerPriceLimit,
            @CookieValue(value = UPPER_PRICE_COOKIE, required = false) BigDecimal upperPriceLimit,
            @CookieValue(value = MINIMAL_PRICE_COOKIE, required = false) BigDecimal minimalPrice,
            @CookieValue(value = MAXIMAL_PRICE_COOKIE, required = false) BigDecimal maximalPrice,
            @CookieValue(value = PAGE_NUMBER_COOKIE, required = false, defaultValue = "0") BigDecimal pageNumber,
            @CookieValue(value = PAGE_SIZE_COOKIE, required = false, defaultValue = "20") BigDecimal pageSize,
            @CookieValue(value = DESCENDING_COOKIE, required = false) Boolean descendingOrder,
            Model model
    ) {
        if (!WebHelper.isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
        model
                .addAttribute("searchString", searchString)
                .addAttribute("category", category)
                .addAttribute("lowerPriceLimit", lowerPriceLimit)
                .addAttribute("upperPriceLimit", upperPriceLimit)
                .addAttribute("minimalPrice", minimalPrice)
                .addAttribute("maximalPrice", maximalPrice)
                .addAttribute("pageNumber", pageNumber)
                .addAttribute("pageSize", pageSize)
                .addAttribute("descendingOrder", descendingOrder);
        return "/product/filter :: widget";
    }

    @GetMapping("/breadcrumbs")
    public String breadCrumbs(
            HttpServletRequest request,
            @CookieValue(value = CATEGORY_ID_COOKIE, required = false) Long categoryId,
            Model model
    ) {
        if (!WebHelper.isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
        List<CategoryDto.Response.All> categories = new LinkedList<>();
        if (null != categoryId) {
            categories.addAll(categoryService.findAncestors(
                    categoryService.findById(categoryId).orElse(null)
            ));
        }
        model.addAttribute("categories", categories);
        return "/product/breadcrumbs :: widget";
    }
}
