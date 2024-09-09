package com.MealTable.meal_table;

import com.MealTable.meal_table.helper.TimeHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedOrigins("http://mealtableui.s3-website.ap-south-1.amazonaws.com")
				.allowedHeaders("*")
				.allowCredentials(true);

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:/home/ec2-user/images/");
	}


}
