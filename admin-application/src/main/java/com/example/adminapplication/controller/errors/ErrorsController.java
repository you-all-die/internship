package com.example.adminapplication.controller.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ivan Gubanov
 */
@Controller
@RequestMapping("/errors")
public class ErrorsController {

    @RequestMapping("/connection")
    public String connectionError() {
        return "/errors/connection";
    }
}
