package com.example.internship.controller.product;

import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.service.CartService;
import com.example.internship.service.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product") // Вынес /product в маппинг(СЮА)
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

    @GetMapping("")
    public String showProducts(
            Model model
    ) {
        final List<ProductDto.Response.AllWithCategoryId> products = gsProductService.findAll();
        final List<CategoryDto.Response.AllWithParentAndSubcategories> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("pageNumber", 1);
        model.addAttribute("pageSize", 20);
        model.addAttribute("total", products.size());
        model.addAttribute("minimalPrice", BigDecimal.ZERO);
        model.addAttribute("maximalPrice", BigDecimal.ZERO);
        return "product/index";
    }

    @PostMapping("")
    public ProductDto.Response.SearchResult showProductList(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "nameLike", required = false) String nameLike,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "minimalPrice", required = false) BigDecimal minimalPrice,
            @RequestParam(value = "maximalPrice", required = false) BigDecimal maximalPrice
    ) {
        return gsProductService.findByCriteria(pageNumber, pageSize, nameLike, categoryId, minimalPrice, maximalPrice);
    }

    @PostMapping("/cart")
    public void addToCart(
            @RequestParam("productId") Product product,
            @CookieValue(value = "customerId", defaultValue = "") String customerId
    ) {
        cartService.add(product, Long.valueOf(customerId));
    }

    @PostMapping("/filter")
    public List<ProductDto.Response.Ids> filter(
            @RequestParam("categoryId") Long categoryId
    ) {
        return gsProductService.findAllIdByCategoryId(categoryId);
    }

    @GetMapping("/cards")
    public String showCards(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            Model model
    ) {
        model.addAttribute(
                "products",
                categoryId == null ? gsProductService.findAll() : gsProductService.findAllByCategoryId(categoryId));
        return "product/cards :: cards";
    }
}