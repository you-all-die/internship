package com.example.internship.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Ivan Gubanov
 */
@Configuration
@EnableSwagger2
// Конфигурация свагера
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Пакет с апишками
                .apis(RequestHandlerSelectors.basePackage("com.example.internship.api"))
                .paths(PathSelectors.any())
                .build();
    }
}
