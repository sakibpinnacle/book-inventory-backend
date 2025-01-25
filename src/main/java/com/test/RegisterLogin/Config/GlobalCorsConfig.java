package com.test.RegisterLogin.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for specific paths
        registry.addMapping("/api/**")  // Apply CORS globally to API endpoints
                .allowedOrigins("http://localhost:3000")  // The frontend URL
                .allowedHeaders("Authorization", "Content-Type")  // Headers allowed in the request
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .exposedHeaders("Authorization");  // Expose Authorization header if needed
    }
}
