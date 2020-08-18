package com.example.demosite.controller;

import com.example.demosite.model.TestData;
import com.example.demosite.repository.TestDataRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestDataController {

    private TestDataRepository testDataRepository;
    final static Logger logger = Logger.getLogger(TestDataController.class);

    @Autowired
    public TestDataController(TestDataRepository userRepository) {
        this.testDataRepository = userRepository;
    }

    @GetMapping({"/", "/test"})
    public String saveDataForm(Model model) {
        model.addAttribute("testData", new TestData());
        List<TestData> list = testDataRepository.findAll();
        model.addAttribute("dataList", list);
        return "index";
    }

    @PostMapping({"/", "/test"})
    public String saveData(@ModelAttribute TestData data, Model model) {
        testDataRepository.save(data);
        logger.info("Saved data: " + data.toString());
        return "redirect:/";
    }

    @RequestMapping(value="/delete", method= RequestMethod.POST)
    public String deleteData(@RequestParam("dataId") String dataId, Model model) {
        logger.info("Removed data ID: " + dataId);
        testDataRepository.deleteById(Long.parseLong(dataId));
        return "redirect:/";
    }

    @RequestMapping(value="/deleteAll", method= RequestMethod.POST)
    public String deleteAllData(Model model) {
        logger.warn("Removed all data!");
        testDataRepository.deleteAll();
        return "redirect:/";
    }
}
