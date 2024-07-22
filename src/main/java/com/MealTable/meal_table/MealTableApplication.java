package com.MealTable.meal_table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.MealTable.meal_table.repository")
public class MealTableApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MealTableApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("*")
				.allowedOrigins("http://localhost:3000")
				.allowedHeaders("*")
				.allowCredentials(true);
	}


}
