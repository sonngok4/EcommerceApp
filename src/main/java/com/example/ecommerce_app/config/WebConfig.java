package com.example.ecommerce_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @SuppressWarnings("null")
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Add static resource handlers for images, CSS, JS
                registry.addResourceHandler("/css/**")
                                .addResourceLocations("classpath:/static/css/");
                registry.addResourceHandler("/js/**")
                                .addResourceLocations("classpath:/static/js/");
                registry.addResourceHandler("/img/**")
                                .addResourceLocations("classpath:/static/img/");
        }
}
