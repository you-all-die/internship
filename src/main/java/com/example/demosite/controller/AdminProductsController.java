package com.example.demosite.controller;

import com.example.demosite.model.Product;
import com.example.demosite.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminProductsController {
    private final ProductRepository productRepository = new ProductRepository();
    final static Logger logger = Logger.getLogger(AdminProductsController.class);

    @GetMapping({"/admin/products"})
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
        return "admin/products";
    }

    @RequestMapping(value="/delete", method= RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") String productId, Model model) {
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        productRepository.removeProduct(productId);
        return "redirect:/admin/products";
    }

    @RequestMapping(value="/edit", method= RequestMethod.GET)
    public String editProduct(@RequestParam("id") String productId, Model model) {
        return "redirect:/admin/edit?id=" + productId;
    }

    @RequestMapping(value="/hide", method= RequestMethod.GET)
    public String hideProduct(@RequestParam("id") String productId, Model model) {
        Product product = productRepository.getProductById(productId);
        product.setStatus("Скрыт");
        return "redirect:/admin/products";
    }

    @RequestMapping(value="/addProduct", method= RequestMethod.GET)
    public String addProduct(Model model) {
        return "redirect:/admin/products";
    }
}
