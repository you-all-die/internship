package com.example.adminapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Ivan Gubanov
 */
@Configuration
public class WebClientConfig {

    @Value("${webclient.url}")
    private String url;

    @Bean
    public WebClient webClient() {

        return WebClient.builder().baseUrl(url).build();
    }
}
