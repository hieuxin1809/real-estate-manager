package com.javaweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imagePath = Paths.get("C:/home/office/building/").toUri().toString();
        registry.addResourceHandler("/building/**") // URL pattern để truy cập ảnh
                .addResourceLocations(imagePath); // Vị trí thư mục ảnh
    }
}
