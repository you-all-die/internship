package com.example.internship.api;


import com.example.internship.entity.ProductStatus;
import com.example.internship.service.ProductStatusService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ivan Gubanov
 */
@RestController
@RequestMapping("/api/product-status/")
@AllArgsConstructor
public class ProductStatusRestController {

    private ProductStatusService productStatusService;

    @PostMapping("/find-all")
    @ApiOperation(value = "Возвращает все статусы", response = List.class)
    public List<ProductStatus> findAll() {
        return productStatusService.findAll();
    }
}
