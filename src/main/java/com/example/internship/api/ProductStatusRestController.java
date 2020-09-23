package com.example.internship.api;


import com.example.internship.entity.ProductStatus;
import com.example.internship.service.ProductStatusService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final ProductStatusService productStatusService;

    @GetMapping("/find-all")
    @ApiOperation(value = "Возвращает все статусы", response = ProductStatus.class)
    public List<ProductStatus> findAll() {
        return productStatusService.findAll();
    }
}
