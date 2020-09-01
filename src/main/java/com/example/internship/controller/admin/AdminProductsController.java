package com.example.internship.controller.admin;


import com.example.internship.entity.Product;
import com.example.internship.service.CategoryService;
import com.example.internship.service.ProductService;
import com.example.internship.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductStatusService productStatusService;

    @GetMapping({"/products"})
    public String findProductAll(Model model) {
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


    @PostMapping(value="/product/hide")
    public String hideProduct(@RequestParam("productId") Long id, Model model) {
        //productService.hideProduct(id);
        return "redirect:/admin/products";
    }

    //Форма редактирования существующего продукта
    @GetMapping(value="/product/{productId}/edit")
    public String editOldProduct(@PathVariable(value = "productId") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("selectcategory", categoryService.findAllSortById());
        return "admin/productsave";
    }

    //Форма добавления нового продукта
    @GetMapping(value="/addnewproduct")
    public String addNewProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("selectcategory", categoryService.findAllSortById());
        return "admin/productsave";
    }

    //Общий метод на добавление и редактирование товара
    @PostMapping(value="/product/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("picture_file") MultipartFile pictureNew,
                              Model model) throws IOException {

        String filePicturename = "default.jpg";
        Boolean renamePicture = false;
        String expansionFile = null;
        String uploadPath = System.getProperty("user.dir") + "/img_product/";

        //Определение расширения файла(изображение товара)
        if (pictureNew.getSize() > 0){
            expansionFile = pictureNew.getOriginalFilename();
            int index = expansionFile.lastIndexOf('.');
            expansionFile = expansionFile.substring(index);
        }

        //Если ID есть->редактирование, иначе добавление нового товара
        //Генерация имени файла
        //Удаление старого файла из папки
        if (product.getId() != null){
            if (pictureNew.getSize() > 0){
                filePicturename = product.getId() + "_" + product.getCategory().getId() + "_" +
                                  product.getName() + expansionFile;

                if (!product.getPicture().equals("default.jpg")){
                    File oldPicture = new File(uploadPath + product.getPicture());
                        if(oldPicture.exists()) {
                            oldPicture.delete();
                        }
                }
            }
            else filePicturename = product.getPicture();
        }
        else {
             renamePicture = true;
        }

            product.setPicture(filePicturename);
            productService.saveProduct(product);

            //Переименование названия фото в БД
            if (renamePicture & (pictureNew.getSize() > 0)){
                long prId = productService.findMaxProduct();
                product = productService.findById(prId);
                filePicturename = product.getId() + "_" + product.getCategory().getId() + "_" +
                                  product.getName()+expansionFile;
                product.setPicture(filePicturename);
                productService.saveProduct(product);
            }

       //Cоздание директории /img_product/, сохранение файла
        if (pictureNew.getSize() > 0) {
            File imgDir = new File(uploadPath);
            if (!imgDir.exists()) {
                imgDir.mkdir();
            }
            pictureNew.transferTo(new File(uploadPath + filePicturename));
        }
        return "redirect:/admin/products";
    }
}
