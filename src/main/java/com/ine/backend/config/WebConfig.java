package com.ine.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Sert les fichiers uploadés depuis /uploads/
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
	}
}
