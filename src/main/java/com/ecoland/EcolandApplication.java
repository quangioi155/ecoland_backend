package com.ecoland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcolandApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcolandApplication.class, args);
	}

}
