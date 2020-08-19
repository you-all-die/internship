package com.example.carcassinternapp.controller;

import com.example.carcassinternapp.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TextController {

    @Autowired
    private TextService textService;

    @GetMapping
    public String home(Model model){
        model.addAttribute("textRows", textService.findall());
        return "home";
    }

    @PostMapping("/insert")
    public String insertText(@RequestParam(name = "value", required = true) String value){
        textService.insert(value);
        return "redirect:";
    }

    @GetMapping("/delete")
    public String deleteRow(@RequestParam(name = "id", required = true) long id){
        textService.delete(id);
        return "redirect:";
    }

    @GetMapping ("/deleteAll")
    public String deleteAll(){
        textService.deleteAll();
        return "redirect:";
    }

}