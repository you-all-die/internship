package com.example.internship.controller.product;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.category.CategoryDto;
import com.example.internship.dto.product.SearchResult;
import com.example.internship.service.ProductService;
import com.example.internship.service.category.GsCategoryService;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.product.GsProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping(ProductController.BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    public static final String BASE_URL = "/product";

    private final ProductService productService;
    private final GsProductService gsProductService;
    private final CustomerService customerService;
    private final GsCategoryService categoryService;

    /**
     * @author Роман Каравашкин
     */
    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") long id, Authentication authentication, Model model) {

        //Получаем дерево предков категорий
        final List<CategoryDto.Response.All> ancestors =
                categoryService.findAncestors(productService.getProductById(id).getCategory().getId());
        model.addAttribute("breadcrumb", ancestors);

        /*Получаем пользователя из авторизации
         * - Если пользователь авторизирован, будет оставлять отзывы от своего имени
        */
        Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);
        customer.ifPresent(customerDto -> model.addAttribute("customerName", true));

        model.addAttribute("product", productService.getProductById(id));
        return "products/product";
    }

    /**
     * Добавление нового комментария к товару
     *
     * @param id код продукта
     * @param customerName имя пользователя
     * @param model модель
     * @return переход на страницу текущего товара
     */
    @PostMapping("/{id}/addNewComment")
    public String addNewCommentProduct(@PathVariable("id") Long id,
                                       @RequestParam(value = "customerName", required = false) String customerName,
                                       @RequestParam(value = "rating", required = false) Integer rating,
                                       Model model){

        return "redirect:" + BASE_URL + "/" + id;
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
        return BASE_URL + "/index";
    }
}
