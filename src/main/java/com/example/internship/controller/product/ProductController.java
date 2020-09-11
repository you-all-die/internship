package com.example.internship.controller.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto.Response.SearchResult;
import com.example.internship.entity.Product;
import com.example.internship.helper.WebHelper;
import com.example.internship.service.CartService;
import com.example.internship.service.ProductService;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.service.product.GsProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private static final String SEARCH_STRING_COOKIE = "productSearchString";
    private static final String CATEGORY_ID_COOKIE = "productCategoryId";
    private static final String LOWER_PRICE_COOKIE = "productLowerPriceLimit";
    private static final String UPPER_PRICE_COOKIE = "productUpperPriceLimit";
    private static final String MINIMAL_PRICE_COOKIE = "productMinimalPrice";
    private static final String MAXIMAL_PRICE_COOKIE = "productMaximalPrice";
    private static final String PAGE_NUMBER_COOKIE = "productPageNumber";
    private static final String PAGE_SIZE_COOKIE = "productPageSize";
    private static final String DESCENDING_COOKIE = "productDescendingOrder";
    private static final String TOTAL_COOKIE = "productTotal";

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
            Model model,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal lowerPriceLimit,
            @RequestParam(required = false) BigDecimal upperPriceLimit,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Boolean descendingOrder
    ) {
        final SearchResult data = gsProductService.findByCriteria(
                searchString, categoryId, lowerPriceLimit, upperPriceLimit, pageNumber, pageSize, descendingOrder
        );
        model.addAttribute("data", data);
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
            Model model,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal lowerPriceLimit,
            @RequestParam(required = false) BigDecimal upperPriceLimit,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Boolean descendingOrder
    ) {
        WebHelper.guardAjaxOrNotFound(request);

        final SearchResult data = gsProductService.findByCriteria(
                searchString, categoryId, lowerPriceLimit, upperPriceLimit, pageNumber, pageSize, descendingOrder
        );
        model.addAttribute("products", data.getProducts());
        return "/product/cards :: widget";
    }

    @GetMapping("/filter")
    public String filter(
            HttpServletRequest request,
            @CookieValue(value = SEARCH_STRING_COOKIE, required = false) String searchString,
            @CookieValue(value = CATEGORY_ID_COOKIE, required = false) Long categoryId,
            @CookieValue(value = LOWER_PRICE_COOKIE, required = false) BigDecimal lowerPriceLimit,
            @CookieValue(value = UPPER_PRICE_COOKIE, required = false) BigDecimal upperPriceLimit,
            @CookieValue(value = MINIMAL_PRICE_COOKIE, required = false) BigDecimal minimalPrice,
            @CookieValue(value = MAXIMAL_PRICE_COOKIE, required = false) BigDecimal maximalPrice,
            @CookieValue(value = DESCENDING_COOKIE, required = false) Boolean descendingOrder,
            Model model
    ) {
        WebHelper.guardAjaxOrNotFound(request);

        List<CategoryDto.Response.AllWithParentSubcategories> categories = categoryService.findTopCategories();

        model
                .addAttribute("searchString", searchString)
                .addAttribute("categoryId", categoryId)
                .addAttribute("categories", categories)
                .addAttribute("lowerPriceLimit", lowerPriceLimit)
                .addAttribute("upperPriceLimit", upperPriceLimit)
                .addAttribute("minimalPrice", minimalPrice)
                .addAttribute("maximalPrice", maximalPrice)
                .addAttribute("descendingOrder", descendingOrder);
        return "/product/filter :: widget";
    }

    @GetMapping("/breadcrumbs")
    public String breadCrumbs(
            HttpServletRequest request,
            @CookieValue(value = CATEGORY_ID_COOKIE, required = false) Long categoryId,
            Model model
    ) {
        WebHelper.guardAjaxOrNotFound(request);

        List<CategoryDto.Response.All> categories = new LinkedList<>();
        if (null != categoryId) {
            categories.addAll(categoryService.findAncestors(
                    categoryService.findById(categoryId).orElse(null)
            ));
        }
        model.addAttribute("categories", categories);
        return "/product/breadcrumbs :: widget";
    }

    @GetMapping("/paginator")
    public String paginator(
            HttpServletRequest request,
            Model model,
            @CookieValue(value = PAGE_NUMBER_COOKIE, required = false, defaultValue = "0") Integer pageNumber,
            @CookieValue(value = PAGE_SIZE_COOKIE, required = false, defaultValue = "20") Integer pageSize,
            @CookieValue(value = TOTAL_COOKIE) Long total
    ) {
        WebHelper.guardAjaxOrNotFound(request);

        /* Костыль, нужный для отображения пагинатора :( */
        int pageCount = (int) (total / pageSize) + 1;
        boolean[] pages = new boolean[pageCount];
        Arrays.fill(pages, false);
        pages[pageNumber] = true;
        /* Конец костыля */

        model
                .addAttribute("pages", pages);
        return "/product/paginator :: widget";
    }
}
