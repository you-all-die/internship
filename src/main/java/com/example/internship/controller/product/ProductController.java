package com.example.internship.controller.product;

import com.example.internship.dto.CustomerDto;
import com.example.internship.dto.product.SearchResult;

import com.example.internship.service.ProductService;
import com.example.internship.service.customer.CustomerService;
import com.example.internship.service.product.GsProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
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

    /**
     * @author Роман Каравашкин
     */
    @GetMapping("/{id}") // убрал ненужный /product (СЮА)
    public String showProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("rate", productService.getProductRating(id));

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
        return BASE_URL + "/index";
    }

    @PostMapping("/add")
    public String addRate(@RequestParam("productId") Long id, @RequestParam("rating") Long rating, Authentication authentication,
                          Model model) {
        Optional<CustomerDto> customer = customerService.getFromAuthentication(authentication);
        if (customer.isPresent()) {
            productService.saveRating(id, customer.get().getId(), rating);
            return "redirect:/product/" + id;
        } else {
            model.addAttribute("product", productService.getProductById(id));
            model.addAttribute("rate", productService.getProductRating(id));
            model.addAttribute("errorMessageRating", "please Authentication");
            return "products/product";
        }
        }
    }

