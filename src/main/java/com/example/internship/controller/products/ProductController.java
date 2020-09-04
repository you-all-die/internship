package com.example.internship.controller.products;

import com.example.internship.service.GsProductService;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product") // Вынес /product в маппинг(СЮА)
@RequiredArgsConstructor // Заменил All на Required (СЮА)
public class ProductController {

    private final ProductService productService;
    private final GsProductService gsProductService;

/*  Убрал, так как реализую свой функционал (СЮА)
    @GetMapping("")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/products";
    }
*/

    /**
     * @Author Роман Каравашкин
     */
    @GetMapping("/{id}") // убрал ненужный /product (СЮА)
    public String showProduct(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/product";
    }

    @GetMapping("")
    public String showProducts() {
        return "product/index";
    }
}
