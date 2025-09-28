package com.ine.backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// TEMPORARY: Allow ALL origins for testing
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));

		// Allow all HTTP methods
		configuration.setAllowedMethods(Arrays.asList("*"));

		// Allow all headers
		configuration.setAllowedHeaders(Arrays.asList("*"));

		// Allow credentials
		configuration.setAllowCredentials(true);

		// Cache preflight response for 1 hour
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
