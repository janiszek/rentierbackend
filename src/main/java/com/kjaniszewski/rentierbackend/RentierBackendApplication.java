package com.kjaniszewski.rentierbackend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j //lombok
@AllArgsConstructor //lombok
@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
//to run Spring Boot applications from a classical WAR archive
public class RentierBackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RentierBackendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RentierBackendApplication.class);
	}
}
