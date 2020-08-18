package com.example.intership.controller;

import com.example.intership.service.WordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("GET /");
        model.addAttribute("words", wordService.findAll());
        return "home";
    }

    @PostMapping("/insert")
    public String insertWord(@RequestParam(name = "value", required = true) String value) {
        log.info("POST /insert");
        wordService.insert(value);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteWord(@RequestParam(name = "id") long id) {
        log.info("GET /delete?id=" + id);
        wordService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/delete-all")
    public String deleteAll() {
        log.info("GET /delete-all");
        wordService.deleteAll();
        return "redirect:/";
    }
}
