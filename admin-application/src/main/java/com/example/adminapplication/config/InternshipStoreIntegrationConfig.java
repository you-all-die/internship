package com.example.adminapplication.config;

import com.example.internship.client.api.CategoryRestControllerApi;
import com.example.internship.client.api.FeedbackRestControllerApi;
import com.example.internship.client.api.ProductRestControllerApi;
import com.example.internship.client.api.ProductStatusRestControllerApi;
import com.example.internship.client.invoker.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternshipStoreIntegrationConfig {

    @Bean
    public CategoryRestControllerApi categoryApi() {
        return new CategoryRestControllerApi(apiClient());
    }

    @Bean
    public ProductStatusRestControllerApi productStatusApi() {
        return new ProductStatusRestControllerApi(apiClient());
    }

    @Bean
    public ProductRestControllerApi productApi() {
        return new ProductRestControllerApi(apiClient());
    }
    @Bean
    public FeedbackRestControllerApi feedbackApi() {
        return new FeedbackRestControllerApi(apiClient());
    }
    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }
}
