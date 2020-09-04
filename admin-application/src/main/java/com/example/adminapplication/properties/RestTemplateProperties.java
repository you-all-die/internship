package com.example.adminapplication.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Gubanov
 */
@Component
@Getter
public class RestTemplateProperties {
    @Value("${resttemplate.url}")
    private String url;
}
