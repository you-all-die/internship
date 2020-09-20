package com.example.adminapplication.controller.products;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.service.CategoryService;
import com.example.adminapplication.service.ProductService;
import com.example.adminapplication.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
@AllArgsConstructor
public class ProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductStatusService productStatusService;
    private static final String DEFAULT_FILE_PICTURE_NAME = "default.jpg";

    //Генерация имени файла
    private String generationFileName(ProductDto product, MultipartFile pictureName) {
        String filePictureName;
        String fileExtension;

        fileExtension = pictureName.getOriginalFilename();
        int index = fileExtension.lastIndexOf('.');
        fileExtension = fileExtension.substring(index);
        filePictureName = product.getId() + "_" + product.getCategory().getId() + fileExtension;
        return filePictureName;
    }

    @GetMapping(value = "/products")
    public String findProduct(@RequestParam(value = "search", required = false) String searchName,
                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "priceFrom", required = false) BigDecimal priceFrom,
                              @RequestParam(value = "priceTo", required = false) BigDecimal priceTo,
                              @RequestParam(value = "categoryId", required = false) Long categoryId,
                              Model model) {

        model.addAttribute("categories", categoryService.findAllSortById());
        model.addAttribute("searchName", searchName);
        model.addAttribute("priceFrom", priceFrom);
        model.addAttribute("priceTo", priceTo);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("searchResult", productService.productSearch(searchName, categoryId, priceFrom,
                priceTo, pageSize, pageNumber));

        return "/products/products";
    }

    //Удаление товара
    @PostMapping(value = "/product/delete")
    public String deleteProduct(@RequestParam("productId") Long id, Model model) {

        productService.removeProduct(id);
        //здесь должны быть проверки на возможность удаления продукта, а пока просто удаляем
        return "redirect:/products";
    }

    @PostMapping(value = "/product/hide")
    public String hideProduct(@RequestParam("productId") Long id, Model model) {
//        productService.hideProduct(id);
        return "redirect:/products";
    }

    //Форма редактирования существующего продукта
    @GetMapping(value = "/product/edit")
    public String editProduct(@RequestParam(value = "productId") Long id, Model model) {

        ProductDto product = productService.findByIdProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("selectcategory", categoryService.findAllSortById());

        return "products/productsave";
    }

    @PostMapping(value = "/product/edit")
    public String editProduct(@RequestParam("productId") String id, Model model) {
        return "redirect:/product/edit?productId=" + id;
    }

    //Форма добавления нового продукта
    @GetMapping(value = "/product/add")
    public String addNewProductForm(Model model) {

        ProductDto product = new ProductDto();
        model.addAttribute("product", product);
        model.addAttribute("product_status", productStatusService.findAll());
        model.addAttribute("selectcategory", categoryService.findAllSortById());

        return "products/productsave";
    }

    //Общий метод на добавление и редактирование товара
    @PostMapping(value = "/product/save")
    public String saveProduct(@ModelAttribute("product") ProductDto product,
                              @RequestParam("picture_file") MultipartFile pictureNew,
                              Model model) throws IOException {


        String filePictureName = DEFAULT_FILE_PICTURE_NAME;
        String defaultPictureName = DEFAULT_FILE_PICTURE_NAME;
        Boolean renamePicture = false;
        String uploadPath = System.getProperty("user.dir") + "/img_product/";

        //Если ID есть->редактирование, иначе добавление нового товара
        //Генерация имени файла
        //Удаление старого файла из папки
        if (product.getId() != null) {
            if (pictureNew.getSize() > 0) {
                filePictureName = generationFileName(product, pictureNew);
                if (!product.getPicture().equals(defaultPictureName)) {
                    File oldPicture = new File(uploadPath + product.getPicture());
                    if (oldPicture.exists()) {
                        oldPicture.delete();
                    }
                }
            } else filePictureName = product.getPicture();
        } else {
            renamePicture = true;
        }

        product.setPicture(filePictureName);
        productService.saveProduct(product);

        //Переименование названия фото в БД
        if (renamePicture & (pictureNew.getSize() > 0)) {
            filePictureName = generationFileName(product, pictureNew);
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

        return "redirect:/products";
    }
}
