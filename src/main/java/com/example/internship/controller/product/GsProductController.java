package com.example.internship.controller.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto;
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
    public String showProducts(
            Model model
    ) {
        final List<ProductDto.Response.AllWithCategoryId> products = gsProductService.findAll();
        final List<CategoryDto.Response.AllWithParentSubcategories> categories = categoryService.findTopCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("pageNumber", 1);
        model.addAttribute("pageSize", 20);
        model.addAttribute("total", products.size());
        model.addAttribute("minimalPrice", BigDecimal.ZERO);
        model.addAttribute("maximalPrice", BigDecimal.ZERO);
        return "product/index";
    }

    @PostMapping
    public ProductDto.Response.SearchResult showProductList(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "nameLike", required = false) String nameLike,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "minimalPrice", required = false) BigDecimal minimalPrice,
            @RequestParam(value = "maximalPrice", required = false) BigDecimal maximalPrice,
            @RequestParam(value = "descending", required = false, defaultValue = "false")  Boolean descending
    ) {
        return gsProductService.findByCriteria(nameLike, categoryId, minimalPrice, maximalPrice, pageSize, pageNumber, descending);
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
            @RequestParam(value = "nameLike", required = false) String nameLike,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "minimalPrice", required = false) BigDecimal minimalPrice,
            @RequestParam(value = "maximalPrice", required = false) BigDecimal maximalPrice,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "descending",required = false) Boolean descending,
            Model model
    ) {
        if (!WebHelper.isAjaxRequest(request)) {
            log.warn("An attempt to access the url " + request.getRequestURL() + " via the browser was detected.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }
        final ProductDto.Response.SearchResult searchResult = gsProductService.findByCriteria(
                nameLike, categoryId, minimalPrice, maximalPrice, 0, 20, descending
        );
        model.addAttribute("products", searchResult.getProducts());
        return "/product/cards :: cards";
    }
}