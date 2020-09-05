package com.example.internship.controller.product;

import com.example.internship.service.GsCategoryService;
import com.example.internship.service.GsProductService;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/product") // Вынес /product в маппинг(СЮА)
@RequiredArgsConstructor // Заменил All на Required (СЮА)
public class GsProductController {

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
        model.addAttribute("products", gsProductService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "product/index";
    }
}
