package com.example.internship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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
                    .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Документация магазина Intership")
                .description("Документация для API магазина.")
                .version("1.0")
                .build();
    }
}
