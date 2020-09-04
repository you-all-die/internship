package com.example.internship.controller.admin.product;

import com.example.internship.dto.product.ProductDto;
import com.example.internship.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Мурашов Алексей
 */
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class TheProductController {

    private final ProductService productService;

    @GetMapping(value="")
    public String showProducts(Model model, @RequestParam(value = "name", required = false) String productName) {
        List<ProductDto.Response.All> list;
        if (productName == null) {
            list = productService.findAll();
        } else {
            list = productService.findByName(productName);
        }
        model.addAttribute("productList", list);
        return "/admin/products";
    }

    @PostMapping(value="")
    public String findProduct(@RequestParam("name") String name) {
        return "redirect:/admin/product?name=" + name;
    }

    @PostMapping(value="/product/delete")
    public String deleteProduct(@RequestParam("productId") Long id) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        productService.delete(id);
        return "redirect:/admin/product";
    }

    @PostMapping(value="/product/edit")
    public String editProduct(@RequestParam("productId") Long id) {
        return "redirect:/admin/product/edit?id=" + id;
    }

    @PostMapping(value="/product/hide")
    public String hideProduct(@RequestParam("productId") Long id) {
        //productService.hideProduct(id);
        return "redirect:/admin/product";
    }

    @PostMapping(value="/product/add")
    public String addProduct() {
        return "redirect:/admin/product/add";
    }
}
