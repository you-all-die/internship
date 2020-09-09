package com.example.internship.controller.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.dto.product.ProductDto.Response.SearchResult;
import com.example.internship.entity.Product;
import com.example.internship.helper.WebHelper;
import com.example.internship.service.CartService;
import com.example.internship.service.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product") // Вынес /product в маппинг (СЮА)
@RequiredArgsConstructor // Заменил All на Required (СЮА)
@Slf4j
public class GsProductController {

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
    public String showAllProducts(
            Model model,
            @CookieValue(value = "productSearchString") String searchString,
            @CookieValue(value = "productCategoryId") Long categoryId,
            @CookieValue(value = "productMinimalPrice") BigDecimal minimalPriceCookie,
            @CookieValue(value = "productMaximalPrice") BigDecimal maximalPrice,
            @CookieValue(value = "productPageNumber", defaultValue = "0") Integer pageNumberCookie,
            @CookieValue(value = "productPageSize", defaultValue = "20") Integer pageSizeCookie,
            @CookieValue(value = "productDescendingOrder") Boolean descendingOrder
    ) {
        final List<ProductDto.Response.AllWithCategoryId> products = gsProductService.findAll();
        final List<CategoryDto.Response.AllWithParentSubcategories> categories = categoryService.findTopCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("pageNumber", pageNumberCookie);
        model.addAttribute("pageSize", pageSizeCookie);
        model.addAttribute("total", products.size());
        model.addAttribute("minimalPrice", minimalPriceCookie);
        model.addAttribute("maximalPrice", maximalPrice);
        return "product/index";
    }

    @PostMapping("/cart")
    public void addToCart(
            @RequestParam("productId") Product product,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
            cartService.add(product, Long.valueOf(customerId));
    }

    @GetMapping("/filter")
    public String filter(
            HttpServletRequest request,
            @CookieValue(value = "productSearchString") String searchString,
            @CookieValue(value = "productCategoryId") Long categoryId,
            @CookieValue(value = "productMinimalPrice") BigDecimal minimalPrice,
            @CookieValue(value = "productMaximalPrice") BigDecimal maximalPrice,
            @CookieValue(value = "productPageNumber") Integer pageNumber,
            @CookieValue(value = "productPageSize") Integer pageSize,
            @CookieValue(value = "productDescendingOrder") Boolean descendingOrder,
            Model model
    ) {
        if (!WebHelper.isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
        final SearchResult searchResult = gsProductService.findByCriteria(
                searchString, categoryId, minimalPrice, maximalPrice, pageNumber, pageSize, descendingOrder
        );
        model.addAttribute("products", searchResult.getProducts());
        return "/product/cards :: cards";
    }

    @GetMapping("/breadcrumbs")
    public String breadCrumbs(
            HttpServletRequest request,
            @CookieValue("productCategoryId") Long categoryId,
            Model model
    ) {
        if (!WebHelper.isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
        model.addAttribute(categoryId);
        return "/product/breadcrumbs :: widget";
    }
}
