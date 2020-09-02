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
    private static final String DEFAULT_FILE_PICTURE_NAME = "default.jpg";

    //Генерация имени файла
    private String generationFileName(Product product, MultipartFile pictureName){
        String filePictureName;
        String fileExtension;

        fileExtension = pictureName.getOriginalFilename();
        int index = fileExtension.lastIndexOf('.');
        fileExtension = fileExtension.substring(index);
        filePictureName = product.getId() + "_" + product.getCategory().getId() + fileExtension;
        return filePictureName;
    }


    @GetMapping(value="/products")
    public String findProduct(@RequestParam(value = "search", required = false) String searchName, Model model) {
        if (searchName==null){
            model.addAttribute("products", productService.findAll());
        }
        else{
                model.addAttribute("products", productService.findByName(searchName));
        }
        return "/admin/products";
    }

    @PostMapping(value="/products")
    public String searchProduct(@RequestParam("search") String searchName, Model model) {
        return "redirect:/admin/products?search=" + searchName;
    }

    //Удаление товара
    @PostMapping(value="/product/delete")
    public String deleteProduct(@RequestParam("productId") Long id, Model model) {
        productService.removeProduct(id);
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        return "redirect:/admin/products";
    }

    //Форма редактирования существующего продукта
    @GetMapping(value="/product/{productId}/edit")
    public String editProduct(@PathVariable(value = "productId") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("selectcategory", categoryService.findAllSortById());
        return "admin/productsave";
    }

    //Форма добавления нового продукта
    @GetMapping(value="/add")
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

        String filePictureName = DEFAULT_FILE_PICTURE_NAME;
        String defaultPictureName = DEFAULT_FILE_PICTURE_NAME;
        Boolean renamePicture = false;
        String uploadPath = System.getProperty("user.dir") + "/img_product/";

        //Если ID есть->редактирование, иначе добавление нового товара
        //Генерация имени файла
        //Удаление старого файла из папки
        if (product.getId() != null){
            if (pictureNew.getSize() > 0){
                filePictureName = generationFileName(product, pictureNew);
                if (!product.getPicture().equals(defaultPictureName)){
                    File oldPicture = new File(uploadPath + product.getPicture());
                        if(oldPicture.exists()) {
                            oldPicture.delete();
                        }
                }
            }
            else filePictureName = product.getPicture();
        }
        else {
             renamePicture = true;
        }

        product.setPicture(filePictureName);
        productService.saveProduct(product);

        //Переименование названия фото в БД
        if (renamePicture & (pictureNew.getSize() > 0)){
            filePictureName = generationFileName(product,pictureNew);
            product.setPicture(filePictureName);
            productService.saveProduct(product);
        }

       //Cоздание директории /img_product/, сохранение файла
        if (pictureNew.getSize() > 0) {
            File imgDir = new File(uploadPath);
            if (!imgDir.exists()) {
                imgDir.mkdir();
            }
            pictureNew.transferTo(new File(uploadPath + filePictureName));
        }
        return "redirect:/admin/products";
    }
}

