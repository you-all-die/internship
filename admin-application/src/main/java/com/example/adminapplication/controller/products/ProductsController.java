package com.example.adminapplication.controller.products;

import com.example.adminapplication.dto.ProductDto;
import com.example.adminapplication.service.CategoryService;
import com.example.adminapplication.service.ProductImageService;
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

import java.math.BigDecimal;

@Controller
@AllArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductStatusService productStatusService;
    private final ProductImageService productImageService;

    @GetMapping(value = "/products")
    public String findProduct(@RequestParam(value = "search", required = false) String searchName,
                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "priceFrom", required = false) BigDecimal priceFrom,
                              @RequestParam(value = "priceTo", required = false) BigDecimal priceTo,
                              @RequestParam(value = "categoryId", required = false) Long categoryId,
                              Model model) {

        model.addAttribute("categories", categoryService.findAll());
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
        productImageService.delete(id);
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
        model.addAttribute("selectcategory", categoryService.findAll());

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
        model.addAttribute("selectcategory", categoryService.findAll());

        return "products/productsave";
    }

    //Общий метод на добавление и редактирование товара
    @PostMapping(value = "/product/save")
    public String saveProduct(@ModelAttribute("product") ProductDto product,
                              @RequestParam("picture_file") MultipartFile pictureNew) {

        if (!pictureNew.isEmpty()) {
            product.setExtension(productImageService.fileExtension(pictureNew));
        }
        ProductDto newProduct = productService.saveProduct(product);

        Long productId = product.getId();
        if (productId == null && newProduct != null) {
            productId = newProduct.getId();
        }

        if (!productImageService.saveOrUpdate(productId, pictureNew)) {
            product.setExtension(null);
            product.setId(productId);
            productService.saveProduct(product);
        }

        return "redirect:/products";
    }
}
