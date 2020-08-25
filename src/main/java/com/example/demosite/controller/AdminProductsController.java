package com.example.demosite.controller;

import com.example.demosite.model.Product;
import com.example.demosite.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {
    private final ProductRepository productRepository = new ProductRepository();
    final static Logger logger = Logger.getLogger(AdminProductsController.class);

    @GetMapping({"/products"})
    public String saveDataForm(Model model, @RequestParam(value = "name", required = false) String productName) {
        List<Product> list;
        if (productName == null) {
            list = productRepository.findAll();
            logger.info("List size: " + list.size());
        } else {
            list = productRepository.findByName(productName);
            logger.info("Name: " + productName + " List size: " + list.size());
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
        productRepository.removeProduct(id);
        return "redirect:/admin/products";
    }

    @PostMapping(value="/product/edit")
    public String editProduct(@RequestParam("productId") Long id, Model model) {
        return "redirect:/admin/product/edit?id=" + id;
    }

    @PostMapping(value="/product/hide")
    public String hideProduct(@RequestParam("productId") Long id, Model model) {
        Product product = productRepository.getProductById(id);
        product.setStatus("Скрыт");
        return "redirect:/admin/products";
    }

    @PostMapping(value="/product/add")
    public String addProduct(Model model) {
        return "redirect:/admin/product/add";
    }
}
