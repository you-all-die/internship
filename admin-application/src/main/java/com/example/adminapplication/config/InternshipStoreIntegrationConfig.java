package com.example.adminapplication.config;

import com.example.internship.client.api.CategoryRestControllerApi;
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
    public ApiClient apiClient() {
        return new ApiClient();
    }
}
