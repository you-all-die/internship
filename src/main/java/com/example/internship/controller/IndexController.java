package com.example.internship.controller;

import com.example.internship.model.TextUser;
import com.example.internship.service.TextUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    TextUserService textUserService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String mainPage(Model model) {
        List<TextUser> textUserList = textUserService.listAll();
        model.addAttribute("textUserList", textUserList);
        model.addAttribute("textUser", new TextUser());
        log.info("/");
        return "index";
    }

    @PostMapping("/add_text")
    public String addText(@ModelAttribute TextUser textUser) {
        textUserService.save(textUser);
        log.info("/add_text");
        return "redirect:/";
    }

    @GetMapping("/delete_all")
    public String deleteAllText() {
        textUserService.deleteAll();
        log.info("/delete_all");
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteText(@RequestParam Long id) {
        textUserService.delete(id);
        log.info("/delete");
        return "redirect:/";
    }
}
