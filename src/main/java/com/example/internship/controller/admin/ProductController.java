package com.example.internship.controller.admin;

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

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
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

    @PostMapping
    public String findProduct(@RequestParam("name") String name) {
        return "redirect:/admin/product?name=" + name;
    }

    @PostMapping("/product/delete")
    public String deleteProduct(@RequestParam("productId") Long id) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        productService.delete(id);
        return "redirect:/admin/product";
    }

    @PostMapping("/product/edit")
    public String editProduct(@RequestParam("productId") Long id) {
        return "redirect:/admin/product/edit?id=" + id;
    }

    @PostMapping("/product/hide")
    public String hideProduct(@RequestParam("productId") Long id) {
        //productService.hideProduct(id);
        return "redirect:/admin/product";
    }

    @PostMapping("/product/add")
    public String addProduct() {
        return "redirect:/admin/product/add";
    }
}
