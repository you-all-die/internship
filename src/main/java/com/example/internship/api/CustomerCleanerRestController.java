package com.example.internship.api;

import com.example.internship.service.customer.CustomerCleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Murashov A.A
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/")
public class CustomerCleanerRestController {
    private final CustomerCleanerService cleanerService;

    @GetMapping("clean")
    public ResponseEntity<String> cleanAnonymousCustomers() {
        Integer removed = cleanerService.cleanInactiveAnonymousCustomers();
        return new ResponseEntity<>("Removed " + removed + " customers", HttpStatus.OK);
    }
}
