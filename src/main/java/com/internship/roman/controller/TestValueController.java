package com.internship.roman.controller;


import com.internship.roman.entity.TestValue;
import com.internship.roman.service.TestService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/test")
@RestController
@Api(value = "/testValue")
public class TestValueController {

    private final TestService testService;
    private static  Logger logger   = LoggerFactory.getLogger(TestValueController.class);

    public TestValueController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping(name = "/post")
    public TestValue saveTestValue(@RequestParam String text){
        logger.info("/post"+" "+text);
        return this.testService.save(text);
    }

    @GetMapping("/getAll")
    public List<TestValue> getAllTestValue(){
        logger.info("/getAll"+" "+"take all");
       return testService.getAll();
    }

    @DeleteMapping("/{id}")
    public Long deleteById(@RequestParam long id){
        testService.deleteById(id);
        logger.info("/{id}"+"delete by Id"+id);
        return id;
    }

    @DeleteMapping("/{all}")
    public void deleteAll(){
        testService.deleteAll();
        logger.info("/{all}"+" delete all");
    }

}
