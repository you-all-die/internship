package com.example.demosite.controller;

import com.example.demosite.model.TestData;
import com.example.demosite.repository.TestDataRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestDataController {
    final private TestDataRepository store = new TestDataRepository();

    @GetMapping({"/", "/test"})
    public String saveDataForm(Model model) {
        model.addAttribute("testData", new TestData());
        List<TestData> list = new ArrayList(store.getStore().values());
        model.addAttribute("dataList", list);
        return "index";
    }

    @PostMapping({"/", "/test"})
    public String saveData(@ModelAttribute TestData data, Model model) {
        store.addData(data);
        return "redirect:/";
    }

    @RequestMapping(value="/delete", method= RequestMethod.POST)
    public String deleteData(@RequestParam("dataId") String dataId, Model model) {
        store.deleteData(dataId);
        return "redirect:/";
    }

    @RequestMapping(value="/deleteAll", method= RequestMethod.POST)
    public String deleteAllData(Model model) {
        store.deleteAllData();
        return "redirect:/";
    }

}
