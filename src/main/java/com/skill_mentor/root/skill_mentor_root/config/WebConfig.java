package com.skill_mentor.root.skill_mentor_root.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.dir:uploads/}")
    private String uploadDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Updated CORS configuration to include your production domains
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://skillmentoe.vercel.app",  // Your Vercel deployment
                        "http://localhost:3000",          // Local development
                        "http://localhost:5173",          // Vite dev server
                        "http://localhost:5174"           // Alternative Vite port
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // Cache preflight response for 1 hour
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve uploaded files statically
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir);
    }
}