package com.example.internship.api;

import com.example.internship.service.customer.CustomerCleanerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/")
@Api(value = "customers")
public class CustomerCleanerRestController {
    private final CustomerCleanerService cleanerService;

    @GetMapping("clean")
    public ResponseEntity<String> cleanAnonymousCustomers() {
        Integer removed = cleanerService.cleanInactiveAnonymousCustomers();
        return new ResponseEntity<>("Removed " + removed + " customers", HttpStatus.OK);
    }
}
