package com.example.internship.controller;

import com.example.internship.model.TextUser;
import com.example.internship.service.TextUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    TextUserService textUserService;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<TextUser> textUserList = textUserService.listAll();
        model.addAttribute("textUserList", textUserList);
        model.addAttribute("textUser", new TextUser());
        return "index";
    }

    @PostMapping("/add_text")
    public String addText(@ModelAttribute TextUser textUser) {
        textUserService.save(textUser);
        return "redirect:/";
    }

    @GetMapping("/delete_all")
    public String deleteAllText() {
        List<TextUser> textUserList = textUserService.listAll();
        for (TextUser item: textUserList) {
            textUserService.delete(item.getId());
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteText(@RequestParam Long id) {
        textUserService.delete(id);
        return "redirect:/";
    }
}
