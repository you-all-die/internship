package com.example.adminapplication;

import com.example.internship.client.api.CategoryRestControllerApi;
import com.example.internship.client.api.FeedbackRestControllerApi;
import com.example.internship.client.api.ProductRestControllerApi;
import com.example.internship.client.api.ProductStatusRestControllerApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CategoryRestControllerApi.class,
        ProductStatusRestControllerApi.class,
        ProductRestControllerApi.class,
        FeedbackRestControllerApi.class})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
