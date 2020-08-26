package com.example.internship.controller;

import com.example.internship.model.Product;
import com.example.internship.repository.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping({"/products"})
    public String saveDataForm(Model model, @RequestParam(value = "name", required = false) String productName) {
        List<Product> list;
        if (productName == null) {
            list = productService.findAll();
        } else {
            list = productService.findByName(productName);
        }
        model.addAttribute("productList", list);
        return "/admin/products";
    }

    @PostMapping(value="/products")
    public String findProduct(@RequestParam("name") String name, Model model) {
        return "redirect:/admin/products?name=" + name;
    }

    @PostMapping(value="/product/delete")
    public String deleteProduct(@RequestParam("productId") Long id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }

    @PostMapping(value="/product/edit")
    public String editProduct(@RequestParam("productId") Long id, Model model) {
        return "redirect:/admin/product/edit?id=" + id;
    }

    @PostMapping(value="/product/hide")
    public String hideProduct(@RequestParam("productId") Long id, Model model) {
        Product product = productService.getProductById(id);
        product.setStatus("Скрыт");
        return "redirect:/admin/products";
    }

    @PostMapping(value="/product/add")
    public String addProduct(Model model) {
        return "redirect:/admin/product/add";
    }
}
