package com.example.internship.controller.admin.goods;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Самохвалов Юрий Алексеевич
 */
@Controller
@RequestMapping("/admin/goods")
public class GoodsController {

    @GetMapping
    public String showGoodsPage() {
        return "/admin/goods/index";
    }
}
