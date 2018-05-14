package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringBootTesteApplication {
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(SpringBootTesteApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTesteApplication.class, args);
	}
}
