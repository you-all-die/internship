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

    @PostMapping(value="/product/delete{productId}")
    public String deleteProduct(@PathVariable(name = "productId") String id, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        productRepository.removeProduct(Long.parseLong(id));
        return "redirect:/admin/products";
    }

    @RequestMapping(value="/edit", method= RequestMethod.GET)
    public String editProduct(@RequestParam("id") String productId, Model model) {
        return "redirect:/edit?id=" + productId;
    }

    @RequestMapping(value="/hide", method= RequestMethod.GET)
    public String hideProduct(@RequestParam("id") String productId, Model model) {
        Product product = productRepository.getProductById(productId);
        product.setStatus("Скрыт");
        return "redirect:/products";
    }

    @RequestMapping(value="/addProduct", method= RequestMethod.GET)
    public String addProduct(Model model) {
        return "redirect:/products";
    }
}
