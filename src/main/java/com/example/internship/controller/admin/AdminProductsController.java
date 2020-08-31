package com.example.internship.controller.admin;

import com.example.internship.dto.ProductDto;
import com.example.internship.entity.Product;
import com.example.internship.repository.ProductRepository;
import com.example.internship.service.CategoryService;
import com.example.internship.service.ProductStatusService;
import com.example.internship.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminProductsController {
    private final ProductServiceImpl productService;
    private final CategoryService categoryService;
    private final ProductStatusService productStatusService;
    private final ProductRepository productRepository;

    @GetMapping({"/products"})
    public String saveDataForm(Model model) {
        model.addAttribute("products", productService.findAll());
        return "/admin/products";
    }

    @PostMapping(value="/products")
    public String findProduct(@RequestParam(value = "search_name", required = false) String name, Model model) {
        return "redirect:/admin/products?name=" + name;
    }

    //Удаление товара
    @GetMapping(value="/product/{productId}/delete")
    public String deleteProduct(@PathVariable(value = "productId") Long id, Model model) {
        productService.removeProduct(id);
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        return "redirect:/admin/products";
    }

    @PostMapping(value="/product/edit/{productId}")
    public String editProduct(@PathVariable(value = "productId") Long id,
                              @RequestParam Long category_id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam("picture_file") MultipartFile picturefile,
                              @RequestParam BigDecimal price,
                              @RequestParam Long product_status,
                              Model model) throws IOException {

        //Проверка файла, создание директории, сохранение в папку /img_product/
        if (picturefile != null) {
            String uploadPath = System.getProperty("user.dir") + "/img_product/";
            File imgDir = new File(uploadPath);
            if (!imgDir.exists()) {
                imgDir.mkdir();
            }
            String uiD = UUID.randomUUID().toString();
            String filename = uiD + picturefile.getOriginalFilename();
            picturefile.transferTo(new File(uploadPath + filename));

            Product product = productRepository.findById(id).orElseThrow();
            product.setCategory(categoryService.findByIdOptional(category_id).get());
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStatus(productStatusService.findById(product_status).get());
            product.setPicture(filename);
            productRepository.save(product);
        }
        return "redirect:/admin/products";
    }


    @PostMapping(value="/product/hide")
    public String hideProduct(@RequestParam("productId") Long id, Model model) {
        //productService.hideProduct(id);
        return "redirect:/admin/products";
    }

    //Форма редактирования существующего продукта
    @GetMapping(value="/product/{productId}/edit")
    public String editOldProduct(@PathVariable(value = "productId") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("category", categoryService.findAll());
        return "admin/productedit";
    }

    //Форма добавления нового продукта
    @GetMapping(value="/addnewproduct")
    public String addNewProduct(Model model) {
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("category", categoryService.findAll());
        return "admin/productadd";
    }

    //Метод добавление нового товара
    @PostMapping(value="/product/add")
    public String addProduct(@RequestParam Long category_id,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam("picture_file") MultipartFile picturefile,
                             @RequestParam BigDecimal price,
                             @RequestParam Long product_status,
                             Model model) throws IOException {

        //Проверка файла, создание директории, сохранение в папку /img_product/
        if (picturefile != null){
            String uploadPath = System.getProperty("user.dir") + "/img_product/";
            File imgDir = new File(uploadPath);
            if(!imgDir.exists()){
                imgDir.mkdir();
            }
            String uiD = UUID.randomUUID().toString();
            String filename = uiD+picturefile.getOriginalFilename();
            picturefile.transferTo(new File(uploadPath + filename));

             ProductDto productDto = new ProductDto();
             productDto.setCategory(categoryService.findByIdOptional(category_id).get());
             productDto.setName(name);
             productDto.setDescription(description);
             productDto.setPrice(price);
             productDto.setStatus(productStatusService.findById(product_status).get());
             productDto.setPicture(filename);
             productService.addProduct(productDto);
        }
        return "redirect:/admin/products";
    }
}
